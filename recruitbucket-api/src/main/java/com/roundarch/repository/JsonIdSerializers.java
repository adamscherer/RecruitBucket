package com.roundarch.repository;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.roundarch.entity.InterviewerEntity;
import com.roundarch.entity.RecruitEntity;

public class JsonIdSerializers {

	private static InterviewerRepository interviewerRepository;

	private static RecruitRepository recruitRepository;

	private static DocumentRepository documentRepository;

	private static DocumentMetadataRepository documentMetadataRepository;

	public static void setInterviewerRepository(InterviewerRepository interviewerRepository) {
		JsonIdSerializers.interviewerRepository = interviewerRepository;
	}

	public static void setRecruitRepository(RecruitRepository recruitRepository) {
		JsonIdSerializers.recruitRepository = recruitRepository;
	}

	public static void setDocumentRepository(DocumentRepository documentRepository) {
		JsonIdSerializers.documentRepository = documentRepository;
	}

	public static void setDocumentMetadataRepository(DocumentMetadataRepository documentMetadataRepository) {
		JsonIdSerializers.documentMetadataRepository = documentMetadataRepository;
	}

	public static class InterviewerSerializer extends JsonSerializer<String> {

		@Override
		public void serialize(String value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
			if (value != null) {
				jgen.writeString(value);

				InterviewerEntity interviewer = interviewerRepository.findOne(value);
				if (interviewer != null) {
					jgen.writeStringField("interviewerUsername", interviewer.getUsername());
					jgen.writeStringField("interviewerFirstName", interviewer.getFirstName());
					jgen.writeStringField("interviewerLastName", interviewer.getLastName());
				}

			} else {
				jgen.writeNull();
			}
		}

	}

	public static class RecruitSerializer extends JsonSerializer<String> {

		@Override
		public void serialize(String value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
			if (value != null) {
				jgen.writeString(value);

				RecruitEntity interviewer = recruitRepository.findOne(value);
				if (interviewer != null) {
					jgen.writeStringField("recruitFirstName", interviewer.getFirstName());
					jgen.writeStringField("recruitLastName", interviewer.getLastName());
				}

			} else {
				jgen.writeNull();
			}
		}

	}
}
