package com.itjfr.jfr.domain;

import java.util.List;

public class NewsDetailInfo {
	public String check_comment_count;
	public String comment_count;
	public List<Comment> comments;

	class Comment {
		public String content;
		public String id;
		public String nickname;
		public String up;

	}

	public String comments_count;
	public List<HotComment> comments_hot;

	class HotComment {
		public String content;
		public String id;
		public String nickname;
		public String up;

	}

	public String comments_hot_count;
	public String content;
	public String down;
	public String id;
	public String source;
	public String title;
	public String up;
	
	
}
