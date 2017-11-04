package org.geojson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.geojson.utils.GeoJsonFileUtils;
import org.geotools.feature.FeatureCollection;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

public class FeatureCollectionTest {
	private static File featureCollectionFile;

	public FeatureCollectionTest() {
		featureCollectionFile = new File("data.json");
	}

	public static void main(String[] args) {
		try {
			new FeatureCollectionTest();
			if (GeoJsonFileUtils.isFeatureCollectionData(featureCollectionFile)) {
				System.out.println("it is a featureCollection !");
				String dirPath = featureCollectionFile.getAbsoluteFile().getParentFile().getAbsolutePath();
				System.out.println("Create FeatureCollection from geoJSONFile : ");
				FeatureCollection<SimpleFeatureType, SimpleFeature> newFeatureCollection = GeoJsonFileUtils.GeoJsonToFeatureCollection(featureCollectionFile);
				GeoJsonFileUtils.displayFeatureCollection(newFeatureCollection);
				System.out.println("Create geoJSONFile from FeatureCollection : ");
				GeoJsonFileUtils.FeatureCollectionToGeoJsonFile(newFeatureCollection, dirPath);
				System.out.println("Voir le fichier --> "+dirPath+File.separator+"outputFeatureCollection.json");
			} else {
				System.out.println("it is not a featureCollection !");
			}
		} catch (FileNotFoundException e) {
			System.out.println("Erreur ! Fichier introuvable !!");
		} catch (IOException e) {
			System.out.println("Erreur entrees sorties !!");
		}
	}

}
