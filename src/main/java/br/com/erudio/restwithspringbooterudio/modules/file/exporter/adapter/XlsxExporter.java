
package br.com.erudio.restwithspringbooterudio.modules.file.exporter.adapter;

import br.com.erudio.restwithspringbooterudio.modules.file.exporter.port.FileExporter;
import br.com.erudio.restwithspringbooterudio.modules.person.Person;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class XlsxExporter implements FileExporter {

    private static final String XLSX_MEDIA_TYPE =
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    private static final String[] HEADERS = {
            "id",
            "first_name",
            "last_name",
            "address",
            "enabled"
    };

    @Override
    public Resource export(List<Person> persons) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Persons");

            CellStyle headerStyle = createCellStyle(workbook);

            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < HEADERS.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(HEADERS[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowIndex = 1;

            for (Person person : persons) {
                Row row = sheet.createRow(rowIndex++);

                row.createCell(0).setCellValue(person.getId());
                row.createCell(1).setCellValue(person.getFirstName());
                row.createCell(2).setCellValue(person.getLastName());
                row.createCell(3).setCellValue(person.getAddress());
                row.createCell(4).setCellValue(
                        Boolean.TRUE.equals(person.getEnabled()) ? "yes" : "no"
                );
            }

            for (int i = 0; i < HEADERS.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(outputStream);

            return new ByteArrayResource(outputStream.toByteArray());

        } catch (IOException exception) {
            throw new IllegalStateException("Failed to export persons to XLSX", exception);
        }
    }

    @Override
    public boolean supports(String acceptHeader) {
        return acceptHeader != null
                && XLSX_MEDIA_TYPE.equalsIgnoreCase(acceptHeader.trim());
    }

    private CellStyle createCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();

        Font font = workbook.createFont();
        font.setFontName("Arial");
        font.setBold(true);

        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);

        return style;
    }
}
