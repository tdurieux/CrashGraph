package github.tdurieux.crashGraph.parser;

import github.tdurieux.crashGraph.entities.Report;

import com.alibaba.fastjson.JSON;

/**
 * A parser of report file
 * @author edmondvanovertveldt
 */
public class ReportParse {
	public static Report parse(String text) {
		return JSON.parseObject(text, Report.class);
	}

	public static Report parse(byte[] bytes) {
		return JSON.parseObject(bytes, Report.class);
	}
}
