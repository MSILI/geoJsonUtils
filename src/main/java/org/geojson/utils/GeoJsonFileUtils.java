package org.geojson.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.geojson.feature.FeatureJSON;
import org.geotools.geojson.geom.GeometryJSON;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.Geometry;

public class GeoJsonFileUtils {

	private static final String POINT = "Point";
	private static final String MULTIPOINT = "MultiPoint";
	private static final String LINESTRING = "LineString";
	private static final String MULTILINESTRING = "MultiLineString";
	private static final String POLYGON = "Polygon";
	private static final String MULTIPOLYGON = "MultiPolygon";
	// private static final String GEOMETRY_COLLECTION = "GeometryCollection";
	private static GeometryJSON geometryJSON;
	private static FeatureJSON featureJSON;

	private static String geoJsonToString(File jsonFile) throws FileNotFoundException, IOException, ParseException {

		JSONParser jsonParser = new JSONParser();
		String data = jsonParser.parse(new FileReader(jsonFile)).toString();

		return data;
	}

	public static boolean isFeatureCollectionData(File jsonFile) throws FileNotFoundException, IOException {

		try {
			String data = geoJsonToString(jsonFile);
			JSONObject jsonData = new JSONObject(data);
			return jsonData.get("type").equals("FeatureCollection");
		} catch (ParseException e) {
			return false;
		} catch (JSONException e) {
			return false;
		}

	}

	public static boolean isGeometryData(File jsonFile) throws FileNotFoundException, IOException {

		try {
			String data = geoJsonToString(jsonFile);
			JSONObject jsonData = new JSONObject(data);
			String type = jsonData.get("type").toString();
			return type.matches(POINT + "|" + MULTIPOINT + "|" + LINESTRING + "|" + MULTILINESTRING + "|" + POLYGON
					+ "|" + MULTIPOLYGON);
		} catch (ParseException e) {
			return false;
		} catch (JSONException e) {
			return false;
		}

	}

	public static void geometryToGeoJsonFile(Geometry geometry, String path) throws FileNotFoundException, IOException {
		geometryJSON = new GeometryJSON();
		geometryJSON.write(geometry, new FileOutputStream(path + File.separator + "outputGeometry.json"));
	}

	public static Geometry geoJsonToGeometry(File geometryFile) throws FileNotFoundException, IOException {
		geometryJSON = new GeometryJSON();
		return geometryJSON.read(new FileInputStream(geometryFile));
	}

	public static void featureCollectionToGeoJsonFile(
			FeatureCollection<SimpleFeatureType, SimpleFeature> featureCollection, String path)
			throws FileNotFoundException, IOException {
		featureJSON = new FeatureJSON();
		featureJSON.writeFeatureCollection(featureCollection,
				new FileOutputStream(new File(path + File.separator + "outputFeatureCollection.json")));
	}

	public static FeatureCollection<SimpleFeatureType, SimpleFeature> geoJsonToFeatureCollection(
			File featureCollectionFile) throws FileNotFoundException, IOException {
		featureJSON = new FeatureJSON();
		return featureJSON.readFeatureCollection(new FileInputStream(featureCollectionFile));
	}
	
	public static void displayFeatureCollection(FeatureCollection<SimpleFeatureType, SimpleFeature> featureCollection) {
		FeatureIterator<SimpleFeature> iterator = featureCollection.features();
		System.out.println("------------------------------------------");
		System.out.println("featureCollection geometries : ");
		while(iterator.hasNext()) {
			SimpleFeature feature = iterator.next();
			System.out.println(feature.getDefaultGeometry());
		}
		System.out.println("------------------------------------------");
	}

}
