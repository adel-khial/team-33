/**
 * Created by dell on 24/04/2017.
 */
import com.aspose.cells.CellArea;
import com.aspose.cells.Cells;
import com.aspose.cells.DataSorter;
import com.aspose.cells.SortOrder;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;


/**
 * Created by Amine on 18/04/2017.
 */
public class AsposeDataSort {
    public void sortAWorksheet(String filepath,String outputPath, int key1, int firstRow,int firstColumn,int nbRows,int lastCol) throws Exception
    {

        // Instantiating a Workbook object
        Workbook workbook = new Workbook(filepath);

        // Accessing the first worksheet in the Excel file
        Worksheet worksheet = workbook.getWorksheets().get(0);

        // Get the cells collection in the sheet
        Cells cells = worksheet.getCells();

        // Obtain the DataSorter object in the workbook
        DataSorter sorter = workbook.getDataSorter();

        // Set the first order
        sorter.setOrder1(SortOrder.ASCENDING);

        // Define the first key.
        sorter.setKey1(key1);

        // Set the second order
        //    sorter.setOrder2(SortOrder.ASCENDING);

        // Define the second key
        //    sorter.setKey2(key2);
        // Create a cells area (range).
        CellArea ca = new CellArea();

        // Specify the start row index.
        ca.StartRow = firstRow;
        // Specify the start column index.
        ca.StartColumn = firstColumn;
        // Specify the last row index.
        ca.EndRow = nbRows;
        // Specify the last column index.
        ca.EndColumn =lastCol;

        // Sort data in the specified data range (A2:C10)
        sorter.sort(cells, ca);

        // Saving the excel file
        workbook.save(outputPath);
    }
}
