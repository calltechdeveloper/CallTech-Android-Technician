package com.calltechservice.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.TypedValue;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.lang.Math.asin;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;

@SuppressWarnings("ALL")
@SuppressLint("ALL")
public class MapDisplayUtils {
	static final double EARTH_RADIUS = 6371009;

	/**
	 * this method is used for showing map in Google API
	 */
	public void showRouteMap(String routes, GoogleMap map) throws Exception {

		PolylineOptions lineOptions = new PolylineOptions();
		MarkerOptions startOptions = new MarkerOptions();
		MarkerOptions endOptions = new MarkerOptions();
		List<LatLng> list = decodePoly(routes);

		lineOptions.addAll(list);
		lineOptions.width(10);
		lineOptions.color(Color.BLUE);
		map.addPolyline(lineOptions);

		// Creating MarkerOptions
		// Setting the position of the marker
		startOptions.position(list.get(0));
		endOptions.position(list.get(list.size() - 1));
		startOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
		endOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
		// Add new marker to the Google Map Android API V2
		map.addMarker(startOptions).setTitle("Start");
		map.addMarker(endOptions).setTitle("Finish");
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(list.get(0), 11));

	}


	public static void showRouteMap(List<Routes> routes, int position, GoogleMap googleMap) throws Exception {
		if (googleMap == null)
			return;

		PolylineOptions lineOptions = new PolylineOptions();
		//encodePath=routes.get(position).overview_polyline.points;
		List<LatLng> list = decodePoly(routes.get(position).overview_polyline.points);
		lineOptions.addAll(list);
		lineOptions.width(10);
		lineOptions.color(Color.BLUE);
		googleMap.addPolyline(lineOptions);
		LatLng startLatLng = new LatLng(routes.get(0).legs.get(0).start_location.lat, routes.get(0).legs.get(0).start_location.lng);
		LatLng endLatLng = new LatLng(routes.get(0).legs.get(0).end_location.lat, routes.get(0).legs.get(0).end_location.lng);

		// Creating MarkerOptions
		MarkerOptions startOptions = new MarkerOptions();
		MarkerOptions endOptions = new MarkerOptions();

		// Setting the position of the marker
		startOptions.position(startLatLng);
		endOptions.position(endLatLng);

		startOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
		endOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

		// Add new marker to the Google Map Android API V2
		googleMap.addMarker(startOptions);
		googleMap.addMarker(endOptions);

		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(endLatLng, 6));
	}

	public static List<LatLng> decodePoly(String encoded) {

		List<LatLng> poly = new ArrayList<>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			LatLng p = new LatLng((((double) lat / 1E5)),
					(((double) lng / 1E5)));
			poly.add(p);
		}

		return poly;
	}


	/**
	 * Encodes a sequence of LatLngs into an encoded path string.
	 */
	public String encode(final List<LatLng> path) {
		long lastLat = 0;
		long lastLng = 0;

		final StringBuffer result = new StringBuffer();

		for (final LatLng point : path) {
			long lat = Math.round(point.latitude * 1e5);
			long lng = Math.round(point.longitude * 1e5);

			long dLat = lat - lastLat;
			long dLng = lng - lastLng;

			encode(dLat, result);
			encode(dLng, result);

			lastLat = lat;
			lastLng = lng;
		}
		return result.toString();
	}

	private void encode(long v, StringBuffer result) {
		v = v < 0 ? ~(v << 1) : v << 1;
		while (v >= 0x20) {
			result.append(Character.toChars((int) ((0x20 | (v & 0x1f)) + 63)));
			v >>= 5;
		}
		result.append(Character.toChars((int) (v + 63)));
	}


	public static String getAddress(Context context, double latitude, double longitude) {
		Geocoder geocoder;
		String strAddress = "";
		List<Address> addressList;
		geocoder = new Geocoder(context, Locale.getDefault());
		try {
			addressList = geocoder.getFromLocation(latitude, longitude, 1);

			if (addressList != null && addressList.size() > 0) {
				Address address = addressList.get(0);
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
					sb.append(address.getAddressLine(i)).append(" ");
				}
				sb.append(address.getLocality()).append(" ");
				sb.append(address.getPostalCode()).append(" ");
				sb.append(address.getCountryName());
				strAddress = sb.toString();

			}

			return strAddress;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}


	public static String getPlace(Context context, double latitude, double longitude) {
		Geocoder geocoder;
		String strAddress = "";
		List<Address> addressList;
		geocoder = new Geocoder(context, Locale.getDefault());
		try {
			addressList = geocoder.getFromLocation(latitude, longitude, 1);

			if (addressList != null && addressList.size() > 0) {
				strAddress = addressList.get(0).getSubLocality();

			}

			return strAddress;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	public static List<Address> getFullAddress(Context context, double latitude, double longitude) {
		Geocoder geocoder;
		String strAddress = "";
		List<Address> addressList;
		geocoder = new Geocoder(context, Locale.getDefault());
		try {
			addressList = geocoder.getFromLocation(latitude, longitude, 1);


			return addressList;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}


	/**
	 * this method is used for showing map in Google API
	 */
	/*public  void showActiveRouteMap(String routes, GoogleMap map, List<ActiveRiders> activeRiders) throws Exception{
        if(map!=null)map.clear();
		PolylineOptions lineOptions  = new PolylineOptions();
		MarkerOptions startOptions = new MarkerOptions();
		MarkerOptions endOptions = new MarkerOptions();
		List<LatLng>  list=decodePoly(routes); 

		

			lineOptions.addAll(list);
			lineOptions.width(10);
			lineOptions.color(Color.BLACK);	
			map.addPolyline(lineOptions);

			// Setting the position of the marker
			startOptions.position(list.get(0));
			endOptions.position(list.get(list.size()-1));
			*//*startOptions.snippet(rideId);
			endOptions.snippet(rideId);*//*
			startOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.pointer));
			endOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.pointer));
			// Add new marker to the Google Map Android API V2
			map.addMarker(startOptions).setTitle("START");
			map.addMarker(endOptions).setTitle("FINISH");
			
			    CircleOptions co = new CircleOptions();
				co.center(list.get(0));
				co.radius(300);
				co.fillColor(Color.TRANSPARENT);
				co.strokeColor(Color.BLACK);
				co.strokeWidth(4.0f);
				map.addCircle(co);
			
		
			  // map.moveCamera(CameraUpdateFactory.newLatLngZoom(list.get(0),16));
			   
	        
	        
			
	    for (ActiveRiders riders : activeRiders) {
	    	
	         	MarkerOptions mapMarkerOptions = new MarkerOptions();
	    	 	
	         	
	         	LatLng latLng = new LatLng(getDoubleValue(riders.lastRecordedLatitude),getDoubleValue(riders.LastRecordedLongitude) );   
	    		
	    	 	
	    	 	mapMarkerOptions.position(latLng);
	    		mapMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
	    		map.addMarker(mapMarkerOptions).setTitle(riders.nameCat);
	     
	       }
	 
	
	}*/
	private double getDoubleValue(String value) {
		try {
			double d = Double.parseDouble(value);
			return d;
		} catch (NumberFormatException e) {

			e.printStackTrace();
			return 0;
		}
	}


	public static double calculateDistance(double fromLatitude, double fromLongitude, double toLatitude, double toLongitude) {

		float results[] = new float[1];

		try {

			Location.distanceBetween(fromLatitude, fromLongitude, toLatitude, toLongitude, results);

		} catch (Exception e) {

			e.printStackTrace();

		}

		int dist = (int) results[0];
		if (dist <= 0) {
			return 0D;
		}

		return results[0];

	}


	public static void animateToMeters(int meters, Context context, GoogleMap googleMap, LatLng point) {
		int mapHeightInDP = 200;
		Resources r = context.getResources();
		int mapSideInPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mapHeightInDP, r.getDisplayMetrics());
		LatLngBounds latLngBounds = calculateBounds(point, meters);
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(latLngBounds, mapSideInPixels, mapSideInPixels, 0);
		if (googleMap != null)
			googleMap.animateCamera(cameraUpdate);
	}

	private static LatLngBounds calculateBounds(LatLng center, double radius) {
		return new LatLngBounds.Builder().
				include(computeOffset(center, radius, 0)).
				include(computeOffset(center, radius, 90)).
				include(computeOffset(center, radius, 180)).
				include(computeOffset(center, radius, 270)).build();
	}


	/**
	 * Returns the LatLng resulting from moving a distance from an origin
	 * in the specified heading (expressed in degrees clockwise from north).
	 *
	 * @param from     The LatLng from which to start.
	 * @param distance The distance to travel.
	 * @param heading  The heading in degrees clockwise from north.
	 */
	public static LatLng computeOffset(LatLng from, double distance, double heading) {
		distance /= EARTH_RADIUS;
		heading = toRadians(heading);
		// http://williams.best.vwh.net/avform.htm#LL
		double fromLat = toRadians(from.latitude);
		double fromLng = toRadians(from.longitude);
		double cosDistance = cos(distance);
		double sinDistance = sin(distance);
		double sinFromLat = sin(fromLat);
		double cosFromLat = cos(fromLat);
		double sinLat = cosDistance * sinFromLat + sinDistance * cosFromLat * cos(heading);
		double dLng = atan2(
				sinDistance * cosFromLat * sin(heading),
				cosDistance - sinFromLat * sinLat);
		return new LatLng(toDegrees(asin(sinLat)), toDegrees(fromLng + dLng));
	}


	public static LatLng getLocationFromAddress(String strAddress, Context context) {

		Geocoder coder = new Geocoder(context);
		List<Address> address;
		LatLng latlang = null;

		try {
			address = coder.getFromLocationName(strAddress, 5);
			if (address == null) {
				return null;
			}
			Address location = address.get(0);
			location.getLatitude();
			location.getLongitude();

			latlang = new LatLng((double) (location.getLatitude() * 1E6),
					(double) (location.getLongitude() * 1E6));
			return latlang;
		} catch (Exception e) {
			e.printStackTrace();
			return latlang;
		}
	}


	//TODO get Address from LatLong
	public static String getAddressFromLatLng( Context context,double latitude, double longitude) {
		Geocoder geocoder = new Geocoder(context);
		String address = "";
		try {
			address = geocoder
					.getFromLocation(latitude, longitude, 1)
					.get(0).getAddressLine(0);
		} catch (IOException e) {
		}

		return address;
	}

}
