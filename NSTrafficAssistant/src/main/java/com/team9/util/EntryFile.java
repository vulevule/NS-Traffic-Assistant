package com.team9.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EntryFile {
	private ArrayList<UtilLine> lines;
	private ArrayList<StopDTO> stops;
	
	public EntryFile() {}
	
	public EntryFile(String fileName) throws JsonParseException, JsonMappingException, IOException  {
		String filePath = System.getProperty("user.dir") + "/" + fileName + ".json";
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			lines = mapper.readValue(new File(filePath), EntryFile.class).getLines();
			stops = mapper.readValue(new File(filePath), EntryFile.class).getStops();
		} catch(FileNotFoundException e) {
			throw new FileNotFoundException(filePath);
		}
		
	}

	public EntryFile(ArrayList<UtilLine> lines, ArrayList<StopDTO> stops) {
		//super();
		this.lines = lines;
		this.stops = stops;
	}

	public ArrayList<UtilLine> getLines() {
		return lines;
	}

	public void setLines(ArrayList<UtilLine> lines) {
		this.lines = lines;
	}

	public ArrayList<StopDTO> getStops() {
		return stops;
	}

	public void setStops(ArrayList<StopDTO> stops) {
		this.stops = stops;
	}
	
	
}

