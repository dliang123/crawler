package com.yt.crawler.webCollector;


import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by jack on 7/21/17.
 */
public class SpuCrawler extends BreadthCrawler {
	/**
	 * 构造一个基于伯克利DB的爬虫
	 * 伯克利DB文件夹为crawlPath，crawlPath中维护了历史URL等信息
	 * 不同任务不要使用相同的crawlPath
	 * 两个使用相同crawlPath的爬虫并行爬取会产生错误
	 *
	 * @param crawlPath 伯克利DB使用的文件夹
	 * @param autoParse 是否根据设置的正则自动探测新URL
	 */
	public SpuCrawler(String crawlPath, boolean autoParse) {
		super(crawlPath, autoParse);
        /*种子页面*/
		this.addSeed("http://www.autohome.com.cn/grade/carhtml/a.html");
		this.addSeed("http://www.autohome.com.cn/grade/carhtml/b.html");
		this.addSeed("http://www.autohome.com.cn/grade/carhtml/c.html");
		this.addSeed("http://www.autohome.com.cn/grade/carhtml/d.html");
		this.addSeed("http://www.autohome.com.cn/grade/carhtml/e.html");
		this.addSeed("http://www.autohome.com.cn/grade/carhtml/f.html");
		this.addSeed("http://www.autohome.com.cn/grade/carhtml/g.html");
		this.addSeed("http://www.autohome.com.cn/grade/carhtml/h.html");
		this.addSeed("http://www.autohome.com.cn/grade/carhtml/i.html");
		this.addSeed("http://www.autohome.com.cn/grade/carhtml/j.html");
		this.addSeed("http://www.autohome.com.cn/grade/carhtml/k.html");
		this.addSeed("http://www.autohome.com.cn/grade/carhtml/l.html");
		this.addSeed("http://www.autohome.com.cn/grade/carhtml/m.html");
		this.addSeed("http://www.autohome.com.cn/grade/carhtml/n.html");
		this.addSeed("http://www.autohome.com.cn/grade/carhtml/o.html");
		this.addSeed("http://www.autohome.com.cn/grade/carhtml/p.html");
		this.addSeed("http://www.autohome.com.cn/grade/carhtml/q.html");
		this.addSeed("http://www.autohome.com.cn/grade/carhtml/r.html");
		this.addSeed("http://www.autohome.com.cn/grade/carhtml/s.html");
		this.addSeed("http://www.autohome.com.cn/grade/carhtml/t.html");
		this.addSeed("http://www.autohome.com.cn/grade/carhtml/u.html");
		this.addSeed("http://www.autohome.com.cn/grade/carhtml/v.html");
		this.addSeed("http://www.autohome.com.cn/grade/carhtml/w.html");
		this.addSeed("http://www.autohome.com.cn/grade/carhtml/x.html");
		this.addSeed("http://www.autohome.com.cn/grade/carhtml/y.html");
		this.addSeed("http://www.autohome.com.cn/grade/carhtml/z.html");


        /*正则规则设置*/
        /*爬取符合 http://news.hfut.edu.cn/show-xxxxxxhtml的URL*/
		this.addRegex("http://www.autohome.com.cn/\\d+/#levelsource=.*");
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
		if (page.matchUrl("http://www.autohome.com.cn/\\d+/#levelsource=.*")) {
//			List<SpuInfoHot> curSpuList = new CopyOnWriteArrayList<>();


            /*we use jsoup to parse page*/
			Document doc = page.doc();
			System.out.println("URL###########" + url);

            /*extract title and content of news by css selector*/
			String title = page.select(".subnav .subnav-title-name h1").text();
			System.out.println("title::::::::::" + title);
			Elements els=page.select(".interval01-list .interval01-list-attention");
			els.forEach(a->{
				String width = a.select("span.attention-value").first().attr("style");
				System.out.println("ELS-WIDTH========" + width);
				Matcher matcher = Pattern.compile("\\d+").matcher(width);
				matcher.find();
				Integer hot = Integer.valueOf(matcher.group());
				String spuId = a.parent().select(".interval01-list-cars-infor p").first().attr("id").split("_")[1];

				System.out.println("ELS-ID========" + spuId);
//				System.out.println("ELS-TITLE========" + a.parent().select(".interval01-list-cars-infor p a").first().text());
				System.out.println("ELS-WIDTH========" + hot);
//				curSpuList.add(new SpuInfoHot(hot,spuId));
			});
//			if (curSpuList!=null&&!curSpuList.isEmpty()){
//				spuAutoHomeHotManager.saveList(curSpuList.stream().map(s -> new SpuAutoHomeHotEntity(s.getHot(), s.getSpuSourceId())).collect(Collectors.toList()));
//			}


//			String content = page.select("div#artibody", 0).text();
//			System.out.println("content:\n" + content);

            /*如果你想添加新的爬取任务，可以向next中添加爬取任务，
               这就是上文中提到的手动解析*/
            /*WebCollector会自动去掉重复的任务(通过任务的key，默认是URL)，
              因此在编写爬虫时不需要考虑去重问题，加入重复的URL不会导致重复爬取*/
            /*如果autoParse是true(构造函数的第二个参数)，爬虫会自动抽取网页中符合正则规则的URL，
              作为后续任务，当然，爬虫会去掉重复的URL，不会爬取历史中爬取过的URL。
              autoParse为true即开启自动解析机制*/
			//next.add("http://xxxxxx.com");
		}
	}

	public static void startCrawler() throws Exception {
		SpuCrawler crawler = new SpuCrawler("auto_home_spu", true);
        /*线程数*/
		crawler.setThreads(30);
        /*设置每次迭代中爬取数量的上限*/
		crawler.setMaxExecuteCount(5000);
        /*设置是否为断点爬取，如果设置为false，任务启动前会清空历史数据。
           如果设置为true，会在已有crawlPath(构造函数的第一个参数)的基础上继
           续爬取。对于耗时较长的任务，很可能需要中途中断爬虫，也有可能遇到
           死机、断电等异常情况，使用断点爬取模式，可以保证爬虫不受这些因素
           的影响，爬虫可以在人为中断、死机、断电等情况出现后，继续以前的任务
           进行爬取。断点爬取默认为false*/
//		crawler.setResumable(true);
        /*开始深度为4的爬取，这里深度和网站的拓扑结构没有任何关系
            可以将深度理解为迭代次数，往往迭代次数越多，爬取的数据越多*/
		crawler.start(2);
	}

	public static void main(String[] args) throws Exception {
		SpuCrawler crawler = new SpuCrawler("auto_home_spu", true);
        /*线程数*/
		crawler.setThreads(50);
        /*设置每次迭代中爬取数量的上限*/
		crawler.setMaxExecuteCount(5000);
        /*设置是否为断点爬取，如果设置为false，任务启动前会清空历史数据。
           如果设置为true，会在已有crawlPath(构造函数的第一个参数)的基础上继
           续爬取。对于耗时较长的任务，很可能需要中途中断爬虫，也有可能遇到
           死机、断电等异常情况，使用断点爬取模式，可以保证爬虫不受这些因素
           的影响，爬虫可以在人为中断、死机、断电等情况出现后，继续以前的任务
           进行爬取。断点爬取默认为false*/
//		crawler.setResumable(true);
        /*开始深度为4的爬取，这里深度和网站的拓扑结构没有任何关系
            可以将深度理解为迭代次数，往往迭代次数越多，爬取的数据越多*/
		crawler.start(2);

//		System.out.println(crawler.curSpuList.size());



	}
}
