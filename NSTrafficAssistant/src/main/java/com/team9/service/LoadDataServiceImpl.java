package com.team9.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.team9.model.Line;
import com.team9.model.Station;
import com.team9.repository.LineRepository;
import com.team9.repository.StationLineRepository;
import com.team9.repository.StationRepository;
import com.team9.util.EntryFile;
import com.team9.util.Stop;
import com.team9.util.StopDTO;
import com.team9.util.UtilLine;
import com.team9.util.UtilLocation;
import com.team9.util.Utils;

@Service
public class LoadDataServiceImpl implements LoadDataService {

	@Autowired
	private StationRepository stationRepository;

	@Autowired
	private LineRepository lineRepository;

	@Autowired
	private StationLineRepository stationlineRepository;

	public LoadDataServiceImpl() {
	}

	@Override
	public List<UtilLine> getParsedDataFromFile(String fileName) throws IOException {
		EntryFile entry = new EntryFile(fileName);

		ArrayList<UtilLine> lines = entry.getLines();
		ArrayList<StopDTO> stopsDTO = entry.getStops();
		ArrayList<Stop> stops = new ArrayList<Stop>();

		stopsDTO = (ArrayList<StopDTO>) stopsDTO.stream().distinct().collect(Collectors.toList());

		// Creating real stops with references on lines -------------------
		for (StopDTO s : stopsDTO) {
			Stop stop = new Stop(s.getName(), s.getLat(), s.getLon(), null);
			ArrayList<UtilLine> tempLines = new ArrayList<UtilLine>();

			for (String lineInStop : s.getLines()) {
				for (UtilLine line : lines) {
					if (lineInStop.equals(line.getName())) {
						tempLines.add(line);
					}
				}
			}

			stop.setLines(tempLines);
			stops.add(stop);
		}
		// -----------------------------------------------------------------

		lines = assignStopsToLines(lines, stops); // Creating stops order and estimated time od arrival (ETA) for every line

		return lines;
	}

	// Lon - X, Lat - Y
	private static ArrayList<UtilLine> assignStopsToLines(ArrayList<UtilLine> lines, ArrayList<Stop> stops) {
		for (UtilLine line : lines) {
			ArrayList<UtilLocation> temp = line.getCoordinates();
			LinkedHashMap<Stop, Double> tempMap = new LinkedHashMap<Stop, Double>();

			for (Stop s : stops) {
				double time = 0;

				if (s.getLines().contains(line)) {
					if (s.getName().contains("OKRETNICA")) {

						double slat = s.getLat();
						double slon = s.getLon();
						double llat0 = temp.get(0).getLat();
						double llon0 = temp.get(0).getLon();
						double llat1 = temp.get(temp.size() - 1).getLat();
						double llon1 = temp.get(temp.size() - 1).getLon();
						if (Utils.distance(new UtilLocation(slat, slon), new UtilLocation(llat0, llon0)) < Utils
								.distance(new UtilLocation(slat, slon), new UtilLocation(llat1, llon1))) {
							temp.add(0, new UtilLocation(s.getLat(), s.getLon()));
							tempMap.put(s, time);
						} else {
							temp.add(new UtilLocation(s.getLat(), s.getLon()));
							for (int i = 0; i < temp.size() - 1; i++) {
								time += Utils.timeForBus(temp.get(i), temp.get(i + 1));
							}
							tempMap.put(s, time);
						}
						continue;
					}

					for (int i = 0; i < temp.size() - 1; i++) {
						UtilLocation l1 = temp.get(i);
						UtilLocation l2 = temp.get(i + 1);

						if (s.getLon() < l2.getLon() && s.getLon() > l1.getLon() && s.getLat() < l2.getLat()
								&& s.getLat() > l1.getLat()) {
							temp.add(i + 1, new UtilLocation(s.getLat(), s.getLon()));
							time += Utils.timeForBus(l1, new UtilLocation(s.getLat(), s.getLon()));
							tempMap.put(s, time);

						} else if (s.getLon() > l2.getLon() && s.getLon() < l1.getLon() && s.getLat() < l2.getLat()
								&& s.getLat() > l1.getLat()) {
							temp.add(i + 1, new UtilLocation(s.getLat(), s.getLon()));
							time += Utils.timeForBus(l1, new UtilLocation(s.getLat(), s.getLon()));
							tempMap.put(s, time);

						} else if (s.getLon() > l2.getLon() && s.getLon() < l1.getLon() && s.getLat() > l2.getLat()
								&& s.getLat() < l1.getLat()) {
							temp.add(i + 1, new UtilLocation(s.getLat(), s.getLon()));
							time += Utils.timeForBus(l1, new UtilLocation(s.getLat(), s.getLon()));
							tempMap.put(s, time);

						} else if (s.getLon() < l2.getLon() && s.getLon() > l1.getLon() && s.getLat() > l2.getLat()
								&& s.getLat() < l1.getLat()) {
							temp.add(i + 1, new UtilLocation(s.getLat(), s.getLon()));
							time += Utils.timeForBus(l1, new UtilLocation(s.getLat(), s.getLon()));
							tempMap.put(s, time);

						} else {
							double slat = s.getLat();
							double slon = s.getLon();
							if (Utils.distance(new UtilLocation(slat, slon), l1) < Utils.distance(l1, l2)
									&& !tempMap.containsKey(s)) {
								temp.add(i + 1, new UtilLocation(s.getLat(), s.getLon()));
								time += Utils.timeForBus(l1, new UtilLocation(s.getLat(), s.getLon()));
								tempMap.put(s, time);
							}

							time += Utils.timeForBus(l1, l2);
						}

					}
				}
			}
			tempMap = tempMap.entrySet().stream().sorted(Entry.comparingByValue())
					.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

			line.setCoordinates(temp);
			line.setStopsAndTimes(tempMap);
		}
		
		return lines;
	}

}
