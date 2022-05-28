package com.example.tourplanner.bl.reports;

import com.example.tourplanner.models.Tour;
import com.example.tourplanner.models.TourLog;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReportManager {

    private String reportDir;
    private String mapDir;


    public ReportManager() {
        this.reportDir = initReportDir();
        this.mapDir = initMapDir();
    }


    public static String initReportDir() {
        Path dir = Paths.get("reports");
        String saveDir = dir.toFile().getAbsolutePath();

        return saveDir;
    }


    public static String initMapDir() {
        Path dir = Paths.get("src", "main", "resources", "com", "example", "tourplanner", "staticmaps");
        String saveDir = dir.toFile().getAbsolutePath();

        return saveDir;
    }


    public void generateTourReport(Tour tour, List<TourLog> tourLogs) {
        reportDir += "\\report_" + tour.getTourId() + ".pdf";

        try {
            PdfWriter pdfWriter = new PdfWriter(reportDir);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);

            // Document title:
            Paragraph header = new Paragraph("SINGLE-TOUR REPORT\n")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                    .setFontSize(20)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(ColorConstants.BLACK);
            document.add(header);


            // Tour Details:
            Paragraph name = new Paragraph("Name: " + tour.getTourName())
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                    .setFontSize(18)
                    .setFontColor(ColorConstants.BLACK);
            document.add(name);

            Paragraph from = new Paragraph("\nFrom: " + tour.getFrom())
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                    .setFontSize(14)
                    .setFontColor(ColorConstants.BLACK);
            document.add(from);

            Paragraph to = new Paragraph("To: " + tour.getTo())
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                    .setFontSize(14)
                    .setFontColor(ColorConstants.BLACK);
            document.add(to);

            Paragraph transportType = new Paragraph("Transport Type: " + tour.getTransportType())
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                    .setFontSize(14)
                    .setFontColor(ColorConstants.BLACK);
            document.add(transportType);

            Paragraph distance = new Paragraph("Distance: " + tour.getDistance())
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                    .setFontSize(14)
                    .setFontColor(ColorConstants.BLACK);
            document.add(distance);

            Paragraph estTime = new Paragraph("Estimated Time: " + tour.getEstTime())
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                    .setFontSize(14)
                    .setFontColor(ColorConstants.BLACK);
            document.add(estTime);

            Paragraph description = new Paragraph("\nTour Description:\n" + tour.getTourDesc())
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                    .setFontSize(14)
                    .setFontColor(ColorConstants.BLACK);
            document.add(description);


            // Tour Logs:
            Paragraph tableHeader = new Paragraph("\nTour Logs:")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                    .setFontSize(18)
                    .setFontColor(ColorConstants.BLACK);
            document.add(tableHeader);

            Table table = new Table(UnitValue.createPercentArray(5)).useAllAvailableWidth();
            table.addHeaderCell(getHeaderCell("Date"));
            table.addHeaderCell(getHeaderCell("Comment"));
            table.addHeaderCell(getHeaderCell("Difficulty"));
            table.addHeaderCell(getHeaderCell("Total Time"));
            table.addHeaderCell(getHeaderCell("Rating"));
            table.setFontSize(14).setBackgroundColor(ColorConstants.WHITE);

            for(int i=0; i<tourLogs.size(); i++) {
                TourLog tourLog = tourLogs.get(i);

                table.addCell(tourLog.getDate().toString());
                table.addCell(tourLog.getComment());
                table.addCell(tourLog.getDifficulty());
                table.addCell(tourLog.getTotalTime());
                table.addCell(tourLog.getRating());
            }
            document.add(table);


            // Tour Map:
            mapDir = initMapDir();
            String mapName = "\\map_" + tour.getTourId() + ".jpg";
            String mapPath = mapDir + mapName;
            File tourMap = new File(mapPath);

            if(tourMap.exists()) {
                mapDir += mapName;
            }
            else {
                mapDir += "\\map-not-found.jpg";
            }

            Paragraph imageHeader = new Paragraph("\nTour Map:")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                    .setFontSize(18)
                    .setFontColor(ColorConstants.BLACK);
            document.add(imageHeader);

            ImageData pathOfImage = ImageDataFactory.create(mapDir);
            Image image = new Image(pathOfImage);
            image.setWidth(450);
            image.setHeight(165);
            document.add(image);

            // Save report:
            document.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    public void generateSummaryReport(List<Tour> allTours, List<TourLog> allTourLogs) {
        reportDir += "\\summary_report.pdf";
        float totalDistance = 0.0f;
        ArrayList<LocalTime> allTourDurations = new ArrayList<>();

        try {
            PdfWriter pdfWriter = new PdfWriter(reportDir);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);

            // Document title:
            Paragraph header = new Paragraph("SUMMARY REPORT\n")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                    .setFontSize(20)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(ColorConstants.BLACK);
            document.add(header);


            Paragraph tourList = new Paragraph("All tours:")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                    .setFontSize(18)
                    .setFontColor(ColorConstants.BLACK);
            document.add(tourList);

            for(int i=0; i<allTours.size(); i++) {
                Tour currTour = allTours.get(i);

                Paragraph name = new Paragraph("- " + currTour.getTourName())
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                        .setFontSize(14)
                        .setFontColor(ColorConstants.BLACK);
                document.add(name);

                // Put all tour durations in an ArrayList for sum calculation:
                LocalTime currDuration = LocalTime.parse(currTour.getEstTime());
                allTourDurations.add(currDuration);

                // Calculate sum of all tour distances:
                totalDistance += currTour.getDistance();
            }


            Paragraph statistics = new Paragraph("\nStatistical Analysis:")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                    .setFontSize(18)
                    .setFontColor(ColorConstants.BLACK);
            document.add(statistics);

            // Total distance and duration:
            Paragraph totalDistancesAmount = new Paragraph("- Total Distances of all Tours: " + totalDistance + "km")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                    .setFontSize(14)
                    .setFontColor(ColorConstants.BLACK);
            document.add(totalDistancesAmount);

            LocalTime totalTime = calcDurationSum(allTourDurations);
            Paragraph totalDurationAmount = new Paragraph("- Total Duration of all Tours: " + totalTime)
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                    .setFontSize(14)
                    .setFontColor(ColorConstants.BLACK);
            document.add(totalDurationAmount);


            // Average distance and duration:
            float avgDistance = totalDistance/ allTours.size();
            avgDistance = Math.round(avgDistance);
            Paragraph avgDistancesAmount = new Paragraph("\n- Average Distances of all Tours: " + avgDistance + "km")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                    .setFontSize(14)
                    .setFontColor(ColorConstants.BLACK);
            document.add(avgDistancesAmount);

            LocalTime avgTime = calcDurationAvg(allTourDurations);
            Paragraph avgDurationAmount = new Paragraph("- Average Duration of all Tours: " + avgTime)
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                    .setFontSize(14)
                    .setFontColor(ColorConstants.BLACK);
            document.add(avgDurationAmount);

            // Save report:
            document.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    private static Cell getHeaderCell(String s) {
        return new Cell().add(new Paragraph(s)).setBold().setBackgroundColor(ColorConstants.LIGHT_GRAY);
    }


    private static LocalTime calcDurationSum(ArrayList<LocalTime> timeList) {
        String tmp = "00:00:00";
        LocalTime tmpLt = LocalTime.parse(tmp);
        long nanoSum = tmpLt.toNanoOfDay();

        for(LocalTime t : timeList) {
            nanoSum += t.toNanoOfDay();
        }

        return LocalTime.ofNanoOfDay(nanoSum);
    }


    private static LocalTime calcDurationAvg(ArrayList<LocalTime> timeList) {
        String tmp = "00:00:00";
        LocalTime tmpLt = LocalTime.parse(tmp);
        long nanoSum = tmpLt.toNanoOfDay();

        for(LocalTime t : timeList) {
            nanoSum += t.toNanoOfDay();
        }

        return LocalTime.ofNanoOfDay(nanoSum/timeList.size());
    }
}
