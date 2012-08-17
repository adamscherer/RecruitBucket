package com.roundarch.util;

import java.io.File;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import com.annconia.api.json.JsonUtils;
import com.annconia.util.StringUtils;
import com.roundarch.entity.College;

public class AccreditationFileConverter {

	public static void main(String... args) {
		new AccreditationFileConverter().run();
	}

	public void run() {
		try {
			Set<College> colleges = parseFile();
			writeFile(colleges);

		} catch (Throwable ex) {
			System.out.println("Error: " + ex);
		}
	}

	public Set<College> parseFile() throws Exception {
		Set<College> colleges = new HashSet<College>();

		ClassPathResource resource = new ClassPathResource("Accreditation_2012_06.csv");
		List<String> lines = IOUtils.readLines(resource.getInputStream());
		for (String line : lines) {
			String[] values = StringUtils.split(line, ',');
			College college = new College();
			college.setId(values[0]);
			college.setName(StringUtils.strip(values[1], "\""));
			college.setAddress(StringUtils.strip(values[2], "\""));
			college.setCity(StringUtils.strip(values[3], "\""));
			college.setState(StringUtils.strip(values[4], "\""));
			college.setZip(StringUtils.strip(values[5], "\""));
			college.setPhone(StringUtils.strip(values[6], "\""));
			college.setWebsite(StringUtils.strip(values[9], "\""));
			colleges.add(college);
		}

		System.out.println("colleges: " + colleges.size());

		return colleges;
	}

	public void writeFile(Set<College> colleges) throws Exception {
		StringBuilder output = new StringBuilder();
		for (College college : colleges) {
			output.append(JsonUtils.toJson(college));
			output.append("\n");
		}

		FileCopyUtils.copy(StringUtils.replace(output.toString(), "\"id\"", "\"_id\""), new FileWriter(new File("c:/colleges.json")));
	}

}
