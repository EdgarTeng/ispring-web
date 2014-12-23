package com.tenchael.ispring.common;

public class PageNavUtil {

	public static String getPageNavHtml(int currentPage, int pageSize,
			int totalRows, int showNums) {

		StringBuilder pageNavHtml = new StringBuilder();

		if (showNums < 1) {
			showNums = 5;
		}

		// ������ҳ��
		int totalPage = (int) Math.ceil(totalRows
				/ Double.parseDouble(String.valueOf(pageSize)));

		// �����м�ҳ������
		int midNum = (int) Math.ceil(Double.parseDouble(String
				.valueOf(showNums)) / 2);

		int beginNum = currentPage <= midNum ? 1 : currentPage - midNum + 1;

		int endNum = beginNum + showNums - 1;

		if (endNum > totalPage) {
			endNum = totalPage;
		}

		// ������1ҳ���� ����ʾ��ҳ����
		if (totalPage > 1) {

			// ��Ҫ��ʾ ��ҳ
			if (currentPage > 1) {
				pageNavHtml.append("<a href='?page=1'> ��ҳ</a>");

			}

			// �������һҳ
			if (currentPage > beginNum) {
				pageNavHtml.append("<a href='?page=" + (currentPage - 1)
						+ "'> &lt; </a>");

			} else {
				pageNavHtml.append("<span class='disabled'> &lt; </span>");
			}

			for (int i = beginNum; i <= endNum; i++) {
				if (i == currentPage) {
					pageNavHtml.append("<span class='current'>" + currentPage
							+ "</span>");
				} else {
					pageNavHtml.append("<a href='?page=" + i + "'>" + i
							+ "</a>");

				}

			}

			// �������һҳ
			if (currentPage < endNum) {
				pageNavHtml.append("<a href='?page=" + (currentPage + 1)
						+ "'> &gt; </a>");

			} else {
				pageNavHtml.append("<span class='disabled'> &gt; </span>");
			}

			// ��Ҫ��ʾ βҳ
			if (currentPage < totalPage) {
				pageNavHtml.append("<a href='?page=" + totalPage + "'>βҳ</a>");
			}

		}

		return pageNavHtml.toString();

	}

	public static String getAjaxPageNavHtml(int currentPage, int pageSize,
			int totalRows, int showNums) {

		StringBuilder pageNavHtml = new StringBuilder();

		if (showNums < 1) {
			showNums = 5;
		}

		// ������ҳ��
		int totalPage = (int) Math.ceil(totalRows
				/ Double.parseDouble(String.valueOf(pageSize)));

		// �����м�ҳ������
		int midNum = (int) Math.ceil(Double.parseDouble(String
				.valueOf(showNums)) / 2);

		int beginNum = currentPage <= midNum ? 1 : currentPage - midNum + 1;

		int endNum = beginNum + showNums - 1;

		if (endNum > totalPage) {
			endNum = totalPage;
		}

		// ������1ҳ���� ����ʾ��ҳ����
		if (totalPage > 1) {

			// ��Ҫ��ʾ ��ҳ
			if (currentPage > 1) {
				pageNavHtml
						.append("<a href='javascript:;' onclick='loadPageData(1,true);'> ��ҳ</a>");
			}

			// �������һҳ
			if (currentPage > beginNum) {
				pageNavHtml
						.append("<a href='javascript:;' onclick='loadPageData("
								+ (currentPage - 1) + ",true);'> &lt; </a>");
			} else {
				pageNavHtml.append("<span class='disabled'> &lt; </span>");
			}

			for (int i = beginNum; i <= endNum; i++) {
				if (i == currentPage) {
					pageNavHtml.append("<span class='current'>" + currentPage
							+ "</span>");
				} else {
					pageNavHtml
							.append("<a href='javascript:;' onclick='loadPageData("
									+ i + ",true);'> " + i + "</a>");
				}

			}

			// �������һҳ
			if (currentPage < endNum) {
				pageNavHtml
						.append("<a href='javascript:;' onclick='loadPageData("
								+ (currentPage + 1) + ",true);'> &gt; </a>");
			} else {
				pageNavHtml.append("<span class='disabled'> &gt; </span>");
			}

			// ��Ҫ��ʾ βҳ
			if (currentPage < totalPage) {
				pageNavHtml
						.append("<a href='javascript:;' onclick='loadPageData("
								+ totalPage + ",true);'>βҳ</a>");
			}

		}

		return pageNavHtml.toString();

	}

}