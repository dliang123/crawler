package com.yt.crawler.webCollector;


import avro.shaded.com.google.common.collect.Lists;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 经纪人信息爬取
 * Created by jack on 7/21/17.
 */
public class JjrCrawler extends BreadthCrawler {

    private List<Jjr> jjrList = Lists.newArrayList();

    public List<Jjr> getJjrList() {
        return jjrList;
    }


    /**
     * 构造一个基于伯克利DB的爬虫
     * 伯克利DB文件夹为crawlPath，crawlPath中维护了历史URL等信息
     * 不同任务不要使用相同的crawlPath
     * 两个使用相同crawlPath的爬虫并行爬取会产生错误
     *
     * @param crawlPath 伯克利DB使用的文件夹
     * @param autoParse 是否根据设置的正则自动探测新URL
     */
    public JjrCrawler(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
        /*种子页面*/
//        for(int i=1;i<=57;i++) {
//			this.addSeed("https://shanghai.anjuke.com/tycoon/j13/p"+i);
//        this.addSeed("https://shanghai.anjuke.com/tycoon/j13/p2/");
//        this.addSeed("https://shanghai.anjuke.com/tycoon/j13/p57/");
//        this.addSeed("https://shanghai.anjuke.com/tycoon/j13/p1/");
//        this.addSeed("https://shanghai.anjuke.com/tycoon/j13/p3/");
//        this.addSeed("https://shanghai.anjuke.com/tycoon/j13/p4/");
//        this.addSeed("https://shanghai.anjuke.com/tycoon/j13/p5/");
//        this.addSeed("https://shanghai.anjuke.com/tycoon/j13/p6/");
//        this.addSeed("https://shanghai.anjuke.com/tycoon/j13/p7/");
//		}

        /*正则规则设置*/
        /*爬取符合 http://news.hfut.edu.cn/show-xxxxxxhtml的URL*/
        this.addRegex("https://shanghai.anjuke.com/tycoon/j13/p.*");
        /*不要爬取 jpg|png|gif*/
        this.addRegex("-.*\\.(jpg|png|gif).*");
        /*不要爬取包含 # 的URL*/
//		this.addRegex("-.*#.*");
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        String url = page.url();
        System.out.println(url);
        /*判断是否为新闻页，通过正则可以轻松判断*/
        if (page.matchUrl("https://shanghai.anjuke.com/tycoon/j13/p.*")) {
//			List<SpuInfoHot> curSpuList = new CopyOnWriteArrayList<>();
            /*we use jsoup to parse page*/
            Document doc = page.doc();
            System.out.println("URL###########" + url);

            Elements els = page.select(".jjr-itemmod");
            for (Element a : els) {
                String name = a.select("a.img").first().attr("title");
                System.out.println("ELS-name========" + name);
                String phone = a.select("div.jjr-side").first().text();
                System.out.println("ELS-phone========" + phone.substring(1, phone.length()));
                Jjr jjr = new Jjr();
                jjr.setName(name);
                jjr.setPhone(phone.substring(1, phone.length()));
                jjrList.add(jjr);
            }

        }
    }

    public static void startCrawler() throws Exception {

        JjrCrawler crawler = new JjrCrawler("ajk_jjr_lj", true);
        /*线程数*/
        crawler.setThreads(1);
        for (int i = 0; i <= 57; i++) {
            crawler.addSeed("https://shanghai.anjuke.com/tycoon/j13/p" + i);
        }
        /*设置每次迭代中爬取数量的上限*/
        crawler.setMaxExecuteCount(5);
        /*设置是否为断点爬取，如果设置为false，任务启动前会清空历史数据。
           如果设置为true，会在已有crawlPath(构造函数的第一个参数)的基础上继
           续爬取。对于耗时较长的任务，很可能需要中途中断爬虫，也有可能遇到
           死机、断电等异常情况，使用断点爬取模式，可以保证爬虫不受这些因素
           的影响，爬虫可以在人为中断、死机、断电等情况出现后，继续以前的任务
           进行爬取。断点爬取默认为false*/
//		crawler.setResumable(true);
        /*开始深度为4的爬取，这里深度和网站的拓扑结构没有任何关系
            可以将深度理解为迭代次数，往往迭代次数越多，爬取的数据越多*/
        crawler.start(1);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("用户表一");
        HSSFRow row = sheet.createRow(0);
        //第四步，创建单元格，设置表头
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("经纪人姓名");
        cell = row.createCell(1);
        cell.setCellValue("电话");

        int i = 0;
        for (Jjr jjr : crawler.getJjrList()) {
            //创建单元格设值
            HSSFRow row1 = sheet.createRow(i + 1);
            row1.createCell(0).setCellValue(jjr.getName());
            row1.createCell(1).setCellValue(jjr.getPhone());
            i++;
        }
        //将文件保存到指定的位置
        try {
            FileOutputStream fos = new FileOutputStream("D:\\jjrinfo.xls");
            workbook.write(fos);
            System.out.println("写入成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        startCrawler();
//        JjrCrawler crawler = new JjrCrawler("ajk_jjr_lj", true);
//        /*线程数*/
//        crawler.setThreads(50);
//        /*设置每次迭代中爬取数量的上限*/
//        crawler.setMaxExecuteCount(5000);
//        /*设置是否为断点爬取，如果设置为false，任务启动前会清空历史数据。
//           如果设置为true，会在已有crawlPath(构造函数的第一个参数)的基础上继
//           续爬取。对于耗时较长的任务，很可能需要中途中断爬虫，也有可能遇到
//           死机、断电等异常情况，使用断点爬取模式，可以保证爬虫不受这些因素
//           的影响，爬虫可以在人为中断、死机、断电等情况出现后，继续以前的任务
//           进行爬取。断点爬取默认为false*/
////		crawler.setResumable(true);
//        /*开始深度为4的爬取，这里深度和网站的拓扑结构没有任何关系
//            可以将深度理解为迭代次数，往往迭代次数越多，爬取的数据越多*/
//        crawler.start(1);

//		System.out.println(crawler.curSpuList.size());


    }

    private class Jjr {
        private String name;
        private String phone;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
