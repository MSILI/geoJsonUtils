package org.geojson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.geojson.utils.GeoJsonFileUtils;

import com.vividsolutions.jts.geom.Geometry;

public class GeometryTest {
	private static File geometryFile;

	public GeometryTest() {
		geometryFile = new File("geometry.json");
	}

	public static void main(String[] args) {
		try {
			new GeometryTest();
			if (GeoJsonFileUtils.isGeometryData(geometryFile)) {
				System.out.println("it is a Geometrie !");
				String dirPath = geometryFile.getAbsoluteFile().getParentFile().getAbsolutePath();
				System.out.println("Create geometry from geoJSONFile : ");
				Geometry geometry =  GeoJsonFileUtils.GeoJsonToGeometry(geometryFile);
				System.out.println(geometry);
				System.out.println("Create geoJSONFile from geometry : ");
				GeoJsonFileUtils.GeometryToGeoJsonFile(geometry, dirPath);
				System.out.println("Voir le fichier --> "+dirPath+File.separator+"outputGeometry.json");
			} else {
				System.out.println("it is not a geomety !");
			}
		} catch (FileNotFoundException e) {
			System.out.println("Erreur ! Fichier introuvable !!");
		} catch (IOException e) {
			System.out.println("Erreur entrees sorties !!");
		}
	}

}
