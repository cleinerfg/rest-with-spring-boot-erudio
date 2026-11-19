package br.com.erudio.restwithspringbooterudio.modules.file.importer.adapter;

import br.com.erudio.restwithspringbooterudio.modules.file.importer.port.FileImporter;
import br.com.erudio.restwithspringbooterudio.modules.person.data.dto.PersonRequest;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class XlsxImporter implements FileImporter {

    private static final DataFormatter FORMATTER = new DataFormatter();

    @Override
    public List<PersonRequest> importFile(InputStream inputStream) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            return parseRows(rowIterator);
        }
    }

    @Override
    public boolean supports(String fileName) {
        return fileName != null && fileName.trim().toLowerCase().endsWith(".xlsx");
    }

    private List<PersonRequest> parseRows(Iterator<Row> rowIterator) {
        List<PersonRequest> personRequests = new ArrayList<>();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (isRowValid(row)) {
                personRequests.add(parseRow(row));
            }
        }

        return personRequests;
    }

    private static boolean isRowValid(Row row) {
        return row != null
                && row.getCell(0) != null
                && row.getCell(0).getCellType() != CellType.BLANK;
    }

    private PersonRequest parseRow(Row row) {
        return PersonRequest.builder()
                .firstName(getCellValue(row, 0))
                .lastName(getCellValue(row, 1))
                .address(getCellValue(row, 2))
                .gender(getCellValue(row, 3))
                .enabled(true)
                .build();
    }

    private String getCellValue(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        if (cell == null) {
            return "";
        }
        return FORMATTER.formatCellValue(cell).trim();
    }
}
