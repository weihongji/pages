package com.jesse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pages {
	private List<InvalidExpression> invalids;
	private HashMap<Integer, DuplicatePage> duplicates;
	private int[] pageArray;

	public Pages(String pages) {
		invalids = new ArrayList<>();
		duplicates = new HashMap<>();
		TreeSet<Integer> pageSet = new TreeSet<>();

		pages = pages == null ? "" : pages.replaceAll("(\\r?\\n)+", ",").replaceAll("\\s+", "");

		if (pages.length() > 0) {
			String[] items = pages.split(",");
			for (int i = 0; i < items.length; i++) {
				String item = items[i];
				if (item.matches("\\d{1,4}")) {
					addPage(pageSet, Integer.parseInt(item));
				}
				else if (item.matches("\\d+-\\d+")) {
					Pattern pattern = Pattern.compile("(\\d{1,4})-(\\d{1,4})");
					Matcher matcher = pattern.matcher(item);
					if (matcher.matches()) {
						int from = Integer.parseInt(matcher.group(1));
						int to = Integer.parseInt(matcher.group(2));
						if (from <= to) {
							for (int j = from; j <= to; j++) {
								addPage(pageSet, j);
							}
						}
						else {
							invalids.add(new InvalidExpression(i + 1, item, "Invalid range"));
						}
					}
					else {
						invalids.add(new InvalidExpression(i + 1, item, "Number larger than 9999"));
					}
				}
				else {
					invalids.add(new InvalidExpression(i + 1, item));
				}
			}
		}
		pageArray = new int[pageSet.size()];
		int i = 0;
		for (Integer integer : pageSet) {
			pageArray[i++] = integer;
		}
	}

	private void addPage(TreeSet<Integer> pageSet, Integer number) {
		boolean added = pageSet.add(number);
		if (!added) {
			DuplicatePage d = duplicates.get(number);
			if (d == null) {
				duplicates.put(number, new DuplicatePage(number));
			}
			else {
				d.increase();
			}
		}
	}

	public int[] getPages() {
		return pageArray;
	}

	public List<InvalidExpression> getInvalids() {
		return invalids;
	}

	public List<DuplicatePage> getDuplicates() {
		return new ArrayList<>(duplicates.values());
	}

	public String getSummary() {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < pageArray.length; i++) {
			list.add(String.valueOf(pageArray[i]));
		}
		String summary = list.toString();
		if (invalids.size() > 0) {
			summary += System.lineSeparator() + "Invalids: " + invalids;
		}
		if (duplicates.size() > 0) {
			summary += System.lineSeparator() + "Duplicates: " + duplicates;
		}

		return summary;
	}

	public class InvalidExpression {
		private int index;
		private String expression;
		private String reason;

		public int getIndex() {
			return index;
		}

		public String getExpression() {
			return expression;
		}

		public String getReason() {
			return reason;
		}

		public InvalidExpression(int index, String expression) {
			this(index, expression, "");
		}

		public InvalidExpression(int index, String expression, String reason) {
			this.index = index;
			this.expression = (expression == null ? "" : expression);
			this.reason = (reason == null ? "" : reason);
		}

		@Override
		public String toString() {
			String s = "#" + index + ": " + expression;
			if (reason.length() > 0) {
				s += " (" + reason + ")";
			}
			return s;
		}
	}

	public class DuplicatePage {
		private int page;
		private int count; // Total number of times that this page appears

		public int getPage() {
			return page;
		}

		public int getCount() {
			return count;
		}

		public DuplicatePage(int page) {
			this(page, 2);
		}

		public DuplicatePage(int page, int count) {
			this.page = page;
			this.count = count;
		}

		public int increase() {
			return ++this.count;
		}

		@Override
		public String toString() {
			return page + " (" + count + ")";
		}
	}
}
