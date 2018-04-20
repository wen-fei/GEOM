package org.grobid.tools;


import org.grobid.core.data.Table;
import org.grobid.core.document.Document;
import org.grobid.core.engines.config.GrobidAnalysisConfig;
import org.grobid.core.factory.*;
import org.grobid.core.main.GrobidHomeFinder;
import org.grobid.core.utilities.*;
import org.grobid.core.engines.Engine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GrobidTools {

    private Engine engine = null;
    private GrobidAnalysisConfig config= null;
    private static final String pGrobidHome = "G:\\GEOM\\grobid\\grobid-home";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public GrobidTools(){
        GrobidHomeFinder grobidHomeFinder = new GrobidHomeFinder(Arrays.asList(pGrobidHome));
        //实例化
        GrobidProperties.getInstance(grobidHomeFinder);

        logger.info(">>>>>>>> GROBID_HOME=" + GrobidProperties.get_GROBID_HOME_PATH());

        this.engine = GrobidFactory.getInstance().createEngine();
        // 加载配置
        this.config = GrobidAnalysisConfig.builder().build();
    }

    protected void finalize() throws Throwable {
        super.finalize();
    }


    /**
     * 从医学文献pdf中抽取摘要
     * @param file
     * @return
     */
    public String getAbstarctFromPDF(File file){
        String content = "";
        try {
            Document document  = this.engine.fullTextToTEIDoc(file, this.config);
            content = engine.getAbstract(document);
            if(content.equals("null")){
                content = "";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 得到一个目录下所有文件
     * @param FilePath
     * @return
     */
    public File[] getFileList(String FilePath){
        File[] files_raw = null;

        try{
            File dir = new File(FilePath);
            if(!dir.isDirectory()){
                return null;
            }else{
                files_raw = dir.listFiles();
            }
        }catch (Exception e){
            logger.info(e + "");
        }
        return files_raw;
    }


    public static void list_to_file(List str, String filePath) throws IOException {
        File file = new File(filePath);
        if(!file.exists()){
            file.createNewFile();
        }else{
            FileOutputStream fos = new FileOutputStream(file);
            for(int i=0; i<str.size(); i++){
                fos.write(String.valueOf(str.get(i)).getBytes());
                fos.write("\n".getBytes());
            }
            fos.close();
        }

    }


    /**
     * 从xml格式的表格数据中抽取出数据，并返回结构化的json格式表格数据
     * @param table_xml
     * @return
     */
    public String xml2json(String table_xml){
        String table_str = "";
        String regEx = "<table>([\\s\\S]*)</table>";
        Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE); // 忽略大小写
        try{
            Matcher matcher = pattern.matcher(table_xml);
            if(matcher.find()){
                table_str = matcher.group(1);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return table_str;

    }


    /**
     * 从一个document中抽取所有的表格数据，并返回json数据
     * @param document
     */
    public void getTableInfoFromPDF(Document document){
        List<Table> tableInfo = document.getTables();
        for (Table table : tableInfo) {
//            String table_xml = table.toTEI(this.config);
//            String table_str = xml2json(table_xml);
            String table_header = table.getHeader();
            String table_tail = table.getCaption();
            String table_content = table.getContent();
            String table_coor = table.getCoordinates();
            String table_label = table.getLabel();
            System.out.println("table_header" + table_header);
            System.out.println("table_tail" + table_tail);
            System.out.println("table_content" + table_content);
            System.out.println("table_coor" + table_coor);
            System.out.println("table_label" + table_label);
        }
    }



    /**
     * 从pdf抽取摘要准确度测试
     * @throws IOException
     */
    public static void exInfoTest() throws IOException {
        GrobidTools grobidTools = new GrobidTools();
        String pdfPath = "D:\\论文库\\知识图谱\\数据集\\";
        String[] file_paths = {"2016-1-3", "2016-4-9", "2016-7-9", "2017-4-6"};
        List<String> error_fils = new ArrayList<String>();
        for(int i = 0; i< file_paths.length; i++){
            System.out.println(pdfPath.concat(file_paths[i]));
            File[] files = grobidTools.getFileList(pdfPath.concat(file_paths[i]));
            for(File file:files){
                String content = grobidTools.getAbstarctFromPDF(file);
                if(content.equals("")){
                    error_fils.add(file.getName());
                }
                System.out.println(file.getName().split("\\.")[0]);
                File file_write = new File("D:\\pdfAbstract\\" + (file.getName().split("\\."))[0] + ".txt");
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file_write);
                fos.write(content.getBytes());
                fos.close();
            }
        }
        list_to_file(error_fils, "D:\\");
    }

    public static void main(String[] args) throws Exception {
        GrobidTools grobidTools = new GrobidTools();
        String PdfFile = "G:\\GEOM\\GEOM\\TikaUse\\data\\26295473.PDF";
        GrobidAnalysisConfig config = grobidTools.config;
        Document document = grobidTools.engine.fullTextToTEIDoc(new File(PdfFile), config);
        grobidTools.getTableInfoFromPDF(document);




    }




}
