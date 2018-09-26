package cn.com.cintel.validatenewskill.controller.v1.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
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

    @RequestMapping(value = "/writeExcel", method = RequestMethod.GET)
    public OutputStream writeExcel(HttpServletRequest request, HttpServletResponse response){

        String fileName = "hello.xlsx";

        //创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        //新建工作表
        XSSFSheet sheet = workbook.createSheet("hello");
        //创建行,0表示第一行
        XSSFRow row = sheet.createRow(0);
        //创建单元格行号由row确定,列号作为参数传递给createCell;第一列从0开始计算
        XSSFCell cell = row.createCell(2);
        //给单元格赋值
        cell.setCellValue("hello sheet");
        //创建输出流
        FileOutputStream fos = null;
        File file = new File("D:\\hello.xlsx");
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(fos);
            workbook.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        byte[] buffer = null;
        try {
            buffer = File2byte("D:\\hello.xlsx");
            System.out.println("buffer.length====="+buffer.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //用tomcat的格式（iso-8859-1）方式去读。
        byte[] a= new byte[0];
        OutputStream toClient =null;
        try {
            a = fileName.getBytes("ISO-8859-1");
            //采用utf-8去接string
            fileName=new String(a,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int browserType = request.getHeader("User-Agent").toUpperCase().indexOf("MSIE");

        if (buffer != null && !"".equals(buffer)) {
            try {
                // 清空response
                response.reset();
                // 解决中文文件名下载时乱码问题
                if (browserType > 0) {
                    fileName = URLEncoder.encode(fileName, "UTF-8");
                } else {
                    fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
                }
                // 设置response的Header
                response.setContentType("application/octet-stream;charset=ISO8859-1");
                response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
                response.addHeader("Pargam", "no-cache");
                response.addHeader("Cache-Control", "no-cache");
                toClient = new BufferedOutputStream(response.getOutputStream());
                toClient.write(buffer);
                toClient.flush();
                toClient.close();
                System.out.println("文件是否删除？"+file.delete());
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return toClient;
    }

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


    public static byte[] File2byte(String filePath)
    {
        byte[] buffer = null;
        try
        {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return buffer;
    }

}
