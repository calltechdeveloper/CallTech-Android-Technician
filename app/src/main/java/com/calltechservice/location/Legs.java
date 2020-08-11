package com.calltechservice.location;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("ALL")
@SuppressLint("ALL")
public class Legs implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public List<Steps> steps;
	public Distance distance;
	public DurationData duration;
	public EndLocation end_location;
	public StartLocation start_location;

}
