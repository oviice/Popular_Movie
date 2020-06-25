package com.ovi.popularmovies.modelClass.detailModel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DetailResponse{

	@SerializedName("id")
	private int id;

	@SerializedName("results")
	private List<ResultsItem> results;

	public int getId(){
		return id;
	}

	public List<ResultsItem> getResults(){
		return results;
	}
}