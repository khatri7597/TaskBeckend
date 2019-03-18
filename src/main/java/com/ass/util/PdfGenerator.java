package com.ass.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

import com.ass.model.WeatherData;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfGenerator {
	public static ByteArrayInputStream generatePdf(List<WeatherData> datas) {
		Document document = new Document(PageSize.A3);
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			PdfWriter.getInstance(document, out);
			document.open();
			Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
			Paragraph para = new Paragraph("Weather Table", font);
			para.setAlignment(Element.ALIGN_CENTER);
			document.add(para);
			document.add(Chunk.NEWLINE);
			
			PdfPTable table = new PdfPTable(10);
			table.setWidthPercentage(100);

			Stream.of("ID", "AIRSPEED", "Date", "Humidity", "Min/Max Temperature", "Temperature", "Pressure", "Weather", "CityId",
					"CityName").forEach(headerTitle -> {
						PdfPCell header = new PdfPCell();
						Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
						header.setBackgroundColor(BaseColor.LIGHT_GRAY);
						header.setHorizontalAlignment(Element.ALIGN_CENTER);
						header.setBorderWidth(1);
						header.setPhrase(new Phrase(headerTitle, headFont));
						table.addCell(header);
					});

			for (WeatherData data : datas) {
				PdfPCell idCell = new PdfPCell(new Phrase(data.getId().toString()));
				idCell.setPaddingLeft(4);
				idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(idCell);

				PdfPCell airSpeed = new PdfPCell(new Phrase(data.getAirspeed().toString()));
				airSpeed.setPaddingLeft(4);
				airSpeed.setVerticalAlignment(Element.ALIGN_MIDDLE);
				airSpeed.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(airSpeed);

				PdfPCell date = new PdfPCell(new Phrase(data.getDate().toString()));
				date.setVerticalAlignment(Element.ALIGN_MIDDLE);
				date.setHorizontalAlignment(Element.ALIGN_RIGHT);
				//date.setPaddingRight(4);
				table.addCell(date);

				PdfPCell humidity = new PdfPCell(new Phrase(data.getHumidity().toString()));
				humidity.setVerticalAlignment(Element.ALIGN_MIDDLE);
				humidity.setHorizontalAlignment(Element.ALIGN_RIGHT);
				//humidity.setPaddingRight(4);
				table.addCell(humidity);

				PdfPCell minnmaxT = new PdfPCell(
						new Phrase(data.getMinT().toString() + "/" + data.getMaxT().toString()));
				minnmaxT.setVerticalAlignment(Element.ALIGN_MIDDLE);
				minnmaxT.setHorizontalAlignment(Element.ALIGN_RIGHT);
				//minnmaxT.setPaddingRight(4);
				table.addCell(minnmaxT);
				
				PdfPCell temp = new PdfPCell(
						new Phrase(data.getTemprature().toString()));
				temp.setVerticalAlignment(Element.ALIGN_MIDDLE);
				temp.setHorizontalAlignment(Element.ALIGN_RIGHT);
				//minnmaxT.setPaddingRight(4);
				table.addCell(temp);

				PdfPCell pressure = new PdfPCell(new Phrase(data.getPressure().toString()));
				pressure.setVerticalAlignment(Element.ALIGN_MIDDLE);
				pressure.setHorizontalAlignment(Element.ALIGN_RIGHT);
				//pressure.setPaddingRight(4);
				table.addCell(pressure);

				PdfPCell weather = new PdfPCell(new Phrase(data.getWeather().toString()));
				weather.setVerticalAlignment(Element.ALIGN_MIDDLE);
				weather.setHorizontalAlignment(Element.ALIGN_RIGHT);
				//weather.setPaddingRight(4);
				table.addCell(weather);

				PdfPCell cityId = new PdfPCell(new Phrase(data.getCityId().toString()));
				cityId.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cityId.setHorizontalAlignment(Element.ALIGN_RIGHT);
				//cityId.setPaddingRight(4);
				table.addCell(cityId);

				PdfPCell cityName = new PdfPCell(new Phrase(data.getCityName().toString()));
				cityName.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cityName.setHorizontalAlignment(Element.ALIGN_RIGHT);
				//cityName.setPaddingRight(4);
				table.addCell(cityName);

			}
			
			document.add(table);

			document.close();

		} catch (Exception e) {
			System.out.println(e);
		}

		return new ByteArrayInputStream(out.toByteArray());	
	}

}
