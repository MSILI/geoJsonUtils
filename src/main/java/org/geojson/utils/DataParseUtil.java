package org.geojson.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Polygon;

public class DataParseUtil {

	private String geoJSONData;

	public DataParseUtil(String data) {
		this.geoJSONData = data;
	}

	public List<JSONObject> getParsedFeatures() throws JSONException {
		JSONArray features = null;
		List<JSONObject> listOfFeatures = null;
		JSONObject featurCollection = new JSONObject(geoJSONData);
		features = featurCollection.getJSONArray("features");

		if (features != null) {
			listOfFeatures = new ArrayList<JSONObject>();
			for (int i = 0; i < features.length(); i++) {
				listOfFeatures.add((JSONObject) features.get(i));
			}
		}

		return listOfFeatures;
	}

	public List<JSONObject> getParsedGeometries() throws JSONException {
		List<JSONObject> listOfGeometries = null;
		List<JSONObject> listOfFeatures = getParsedFeatures();
		if (listOfFeatures != null) {
			listOfGeometries = new ArrayList<JSONObject>();
			for (JSONObject jo : listOfFeatures) {
				listOfGeometries.add((JSONObject) jo.get("geometry"));
			}
		}
		return listOfGeometries;
	}

	public List<JSONArray> getCoordinates(String geometryType) throws JSONException {
		List<JSONArray> listOfLineStrings = null;
		List<JSONObject> listOfGeometries = getParsedGeometries();
		if (listOfGeometries != null) {
			listOfLineStrings = new ArrayList<JSONArray>();
			for (JSONObject geometry : listOfGeometries) {
				if (geometry.get("type").equals(geometryType)) {
					listOfLineStrings.add((JSONArray) geometry.get("coordinates"));
				}
			}
		}
		return listOfLineStrings;
	}

	public List<LineString> ParseLineStringCoordinate() throws JSONException {
		List<LineString> listOfParsedLineString = null;
		List<JSONArray> listOfLineStrings = getCoordinates("LineString");
		GeometryFactory factory = new GeometryFactory();
		if (listOfLineStrings != null) {
			listOfParsedLineString = new ArrayList<LineString>();
			for (JSONArray line : listOfLineStrings) {
				Coordinate[] coords = new Coordinate[line.length()];
				for (int j = 0; j < line.length(); j++) {
					JSONArray coordinate = (JSONArray) line.get(j);
					double x = coordinate.getDouble(0);
					double y = coordinate.getDouble(1);
					coords[j] = new Coordinate(x, y);
				}
				LineString ls = factory.createLineString(coords);
				listOfParsedLineString.add(ls);
			}
		}
		return listOfParsedLineString;
	}

	public List<Polygon> parsePolygonesCoordinates()
			throws JSONException{

		List<Polygon> listOfParsedPolygones = null;
		List<JSONArray> listOfPolygones = getCoordinates("Polygon");
		;
		GeometryFactory factory = new GeometryFactory();
		if (listOfPolygones != null) {
			listOfParsedPolygones = new ArrayList<Polygon>();
			for (JSONArray line : listOfPolygones) {
				JSONArray coordinatesArray = line.getJSONArray(0);
				Coordinate[] coords = new Coordinate[coordinatesArray.length()];
				for (int j = 0; j < coordinatesArray.length(); j++) {
					JSONArray coordinate = (JSONArray) coordinatesArray.get(j);
					double x = coordinate.getDouble(0);
					double y = coordinate.getDouble(1);
					coords[j] = new Coordinate(x, y);
				}
				Polygon pl = factory.createPolygon(coords);
				listOfParsedPolygones.add(pl);
			}
		}

		return listOfParsedPolygones;
	}

	public void displayData() throws JSONException{

		for (LineString ls : ParseLineStringCoordinate()) {
			System.out.println("Line string : ");
			for (Coordinate coordinate : ls.getCoordinates()) {
				System.out.println("x=" + coordinate.x + " y=" + coordinate.y);
			}
			System.out.println("-----------------------------------------------------------");
		}

		for (Polygon pl : parsePolygonesCoordinates()) {
			System.out.println("Polygon : ");
			for (Coordinate coordinate : pl.getCoordinates()) {
				System.out.println("x=" + coordinate.x + " y=" + coordinate.y);
			}
			System.out.println("-----------------------------------------------------------");
		}

	}

	public String getDataPath() {
		return geoJSONData;
	}

	public void setDataPath(String dataPath) {
		this.geoJSONData = dataPath;
	}

}
