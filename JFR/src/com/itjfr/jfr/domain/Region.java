package com.itjfr.jfr.domain;

import java.util.List;

public class Region {
	public List<City> contry;

	public class City {
		public String id;
		public String name;
		public List<Area> city;
		
		public class Area{
			public String id;
			public String name;
			public List<District> district;
			
			public class District{
				public String id;
				public String name;
			}
		}
	}
}
