package org.miktmc.packages;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.bson.json.JsonWriterSettings;
import org.springframework.stereotype.Component;

@Component
public class JsonWriterSettingsConstructor {

	public JsonWriterSettings getSettings() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		JsonWriterSettings settings = JsonWriterSettings.builder()
				.stringConverter((value, writer) -> writer.writeString(value.toString()))
				.int64Converter((value, writer) -> writer.writeNumber(value.toString()))
				.timestampConverter((value, writer) -> writer.writeString(dateFormat.format(value.getTime())))
				.dateTimeConverter(
						(value, writer) -> writer.writeString(dateFormat.format(new Date(value.longValue()))))
				.build();
		return settings;
	}

}
