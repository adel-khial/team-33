package com.team33.model.csv;

import com.team33.model.Utilities.Util;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Amine on 13/02/2017.
 */
public class AssigningTeacherToCourseFormat extends UserFormat implements CSVFormat {

    public AssigningTeacherToCourseFormat(boolean isGenerated) {
        super(isGenerated);
    }

    public void generateHeaderInTempFile(XSSFWorkbook workbook, int maxColumn) {
        Row row = workbook.getSheetAt(0).getRow(0);
        int j = 1;
        for (int i = 5; i < maxColumn+4;i += 2)
        {
            row.createCell(i);
            row.createCell(i+1);
            row.getCell(i).setCellValue("course"+j);
            row.getCell(i+1).setCellValue("type"+j);
            j++;
        }
    }
    private void AddInformation(ArrayList<String> arrayList,String info,boolean isCourse){
        if (!(info == null||info.isEmpty()) ){
            if (isCourse){
                arrayList.add(info.replace("Coordination","")
                        .replace("+","").substring(info.lastIndexOf("*")+1).toUpperCase().replace(" ",""));
                arrayList.add(String.valueOf(2));
            }
            else {
                if (!arrayList.contains(info.substring(info.lastIndexOf("*")+1).toUpperCase().replace(" ",""))){
                    arrayList.add(info.replace("Coordination","")
                            .replace("+","").substring(info.lastIndexOf("*")+1).toUpperCase().replace(" ",""));
                    arrayList.add(String.valueOf(3));

                }

            }
        }

    }
    private void getAssignedCourses(Row row, int firstCourse, int CourseColumn, ArrayList<String> arrayList){
        String info = row.getCell(CourseColumn).toString();
        boolean isCourse = false;
        if ((firstCourse % 2) == (CourseColumn % 2)) isCourse = true;
       if (info.contains("\n")){
           while (info.contains("\n")){
               String info1 = info.substring(0,info.indexOf('\n'));
               AddInformation(arrayList,info1,isCourse);
               info = info.substring(info.indexOf('\n')+1);
           }
       }
        else AddInformation(arrayList,info,isCourse);
    }
    public void generateRow(int numRow,int index ,ArrayList<String> arrayList)
    {
        Row rw = getWorkbookOut().getSheetAt(0).getRow(numRow);
        for (int j = index; j < index + arrayList.size(); j++) {
            rw.createCell(j).setCellValue(arrayList.get(j-index));
        }
    }
    @Override
    public String buildCSV(ArrayList<String> workbooksPaths) throws IOException, InvalidFormatException {
        String workbookPath = workbooksPaths.get(0);
        String emailWorkbookPath = workbooksPaths.get(1);
        openWorkbookIn(workbookPath);
        openEmailWorkbook(emailWorkbookPath);
        TeacherFormat teacherFormat = new TeacherFormat(this.isGeneratedPassword());
        String tempFile = teacherFormat.buildCSV(workbooksPaths);
        FileInputStream fileInputStream = new FileInputStream(tempFile);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        setWorkbookOut(workbook);
        Iterator<Row> rowIterator = getWorkbookIn().getSheetAt(0).rowIterator();
        Row row = rowIterator.next();
        int maxColumn = 0 ;
        boolean found = false ;
        while ((rowIterator.hasNext()) && (!found)){
            if (rowContains(row,"NOM")) found = true;
            else row = rowIterator.next();
        }
        int firstCourseColumn = Util.getInstance().column(row,"COURS\n" + "SEMESTRE1");
        ArrayList<String>  arrayList = new ArrayList<>();
        int numRow = 1 ;
        while (rowIterator.hasNext()){
            row = rowIterator.next();
            for (int i = firstCourseColumn ; i < firstCourseColumn+5 ; i++) getAssignedCourses(row,firstCourseColumn,i,arrayList);
            generateRow(numRow,5, (ArrayList<String>) arrayList.clone());
            if (arrayList.size() > maxColumn) maxColumn = arrayList.size();
            arrayList.clear();
            numRow++;
        }
        generateHeaderInTempFile(workbook,maxColumn);
        File file = new File("CreatingTeachersWithCourses.xlsx");
        saveUsersList(file);
        new File(tempFile).delete();
        return file.getPath() ;
    }
}
