package com.calltechservice.location;

import android.annotation.SuppressLint;

import java.io.Serializable;

@SuppressWarnings("ALL")
@SuppressLint("ALL")
public class Steps implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public PolylineData polyline;
	public Distance distance;
	public DurationData duration;
	public String html_instructions;
	public EndLocation end_location;
	public StartLocation start_location;
}
