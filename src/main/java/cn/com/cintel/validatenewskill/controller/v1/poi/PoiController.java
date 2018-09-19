package cn.com.cintel.validatenewskill.controller.v1.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;

/**
 * @Company Name:
 * @Author: sky
 * @CreatDate: 2018/9/19 16:18
 * @ClassName: cn.com.cintel.validatenewskill.controller.v1.poi
 * @Description: 读取excel的poi操作类
 * @Modified By:
 * @ModifyDate: 2018/9/19 16:18
 */
@RestController
@RequestMapping(value = "/poi")
public class PoiController {

    @GetMapping(value = "/readExcel",produces = "application/json;charset=utf-8")
    public void readExcel(){

        try {
            // 同时支持Excel 2003、2007
            // 创建文件对象
            File excelFile = new File("d:/area.xls");
            // 文件流
            FileInputStream in = new FileInputStream(excelFile);
            ExcelUtil.checkExcelVaild(excelFile);
            Workbook workbook = ExcelUtil.getWorkbok(in,excelFile);
            // 这种方式 Excel2003/2007/2010都是可以处理的
            //Workbook workbook = WorkbookFactory.create(is);

            // Sheet的数量
            int sheetCount = workbook.getNumberOfSheets();
            /**
             * 设置当前excel中sheet的下标：0开始
             */
            // 遍历第一个Sheet
            Sheet sheet = workbook.getSheetAt(1);
            // 遍历第三个Sheet
//            Sheet sheet = workbook.getSheetAt(2);

            //获取总行数
//            System.out.println(sheet.getLastRowNum());

            // 为跳过第一行目录设置count
            int count = 0;
            for (Row row : sheet) {
                try {
                    // 跳过第一和第二行的目录
                    if(count < 2 ) {
                        count++;
                        continue;
                    }

                    //如果当前行没有数据，跳出循环
                    if(row.getCell(0).toString().equals("")){
                        return;
                    }

                    //获取总列数(空格的不计算)
                    int columnTotalNum = row.getPhysicalNumberOfCells();
                    System.out.println("总列数：" + columnTotalNum);

                    System.out.println("最大列数：" + row.getLastCellNum());

                    int end = row.getLastCellNum();
                    for (int i = 0; i < end; i++) {
                        Cell cell = row.getCell(i);
                        if(cell == null) {
                            System.out.print("null" + "\t");
                            continue;
                        }

                        Object obj = ExcelUtil.getValue(cell);
                        System.out.print(obj + "\t");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
