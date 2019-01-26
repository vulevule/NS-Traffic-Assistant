package com.team9.service;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.team9.util.UtilLine;

public interface LoadDataService {
	List<UtilLine> getParsedDataFromFile(String fileName) throws JsonParseException, JsonMappingException, IOException;
	
	//List<Line> getLines(String fileName);
}
