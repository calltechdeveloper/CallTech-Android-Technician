package com.calltechservice.location;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("ALL")
@SuppressLint("ALL")
public class Routes implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public List<Legs> legs;
	public OverviewPolyline overview_polyline;
	public String summary;
}
