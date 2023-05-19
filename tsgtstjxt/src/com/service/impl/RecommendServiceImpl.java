package com.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.BooksDAO;
import com.dao.OrdersDAO;
import com.dao.UsersDAO;
import com.entity.Books;
import com.entity.Orders;
import com.entity.Users;
import com.service.RecommendService;

@Service("recommendService")
public class RecommendServiceImpl implements RecommendService {

	// 注入DAO对象
	@Autowired
	@Resource
	private BooksDAO booksDAO;
	@Autowired
	@Resource
	private UsersDAO usersDAO;
	@Autowired
	@Resource
	private OrdersDAO ordersDAO;
	private String userid = "";
	// 相似用户集合
	private List<List<Object>> similarityUsers = null;
	// 推荐所有图书集合
	private List<String> targetRecommendBooks = null;
	// 借阅过图书集合
	private List<String> commentedBooks = null;
	// 用户在图书借阅集合中的位置
	private int targetUserIndex = 0;
	// 目标用户借阅过的图书
	private List<String> targetUserCommentedBooks = null;

	private String[] books = null;

	@Override
	public List<Books> getRecommend(String userid) {
		this.userid = userid;
		// 建立用户数组 除了当前用户外 随机抽取9个用户
		String[] users = new String[10];
		users[0] = this.userid;
		List<Users> usersList = this.usersDAO.getUsers(this.userid);
		System.out.println("users == > " + usersList.size());
		for (int i = 0; i < 9; i++) {
			int j = i + 1;
			int tbNum = usersList.size();
			if (i < tbNum) {
				users[j] = usersList.get(i).getUsersid();
			} else {
				users[j] = "0";
			}
		}
		List<Books> booksList = this.booksDAO.getAllBooks();
		this.books = new String[booksList.size()];
		for (int j = 0; j < booksList.size(); j++) {
			this.books[j] = booksList.get(j).getBooksid();
		}
		// 建立借阅二维数组 用户借阅了图书 1 未借阅 0 之后计算用户的相似度
		int[][] allUserBooks = new int[10][booksList.size()];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < booksList.size(); j++) {
				String booksid = this.books[j];
				Orders orders = new Orders();
				orders.setUsersid(users[i]);
				orders.setBooksid(booksid);
				List<Orders> ordersList = this.ordersDAO.getOrdersByCond(orders);
				if (ordersList.size() == 0) {
					allUserBooks[i][j] = 0;
				} else {
					allUserBooks[i][j] = 1;
				}
			}
		}
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < booksList.size(); j++) {
				if (j == booksList.size() - 1) {
					System.out.print(allUserBooks[i][j]);
				} else {
					System.out.print(allUserBooks[i][j] + " ,");
				}
			}
			System.out.println("");
		}
		this.targetUserCommentedBooks = new ArrayList<String>();
		Orders orders = new Orders();
		orders.setUsersid(this.userid);
		List<Orders> ordersList = this.ordersDAO.getOrdersByCond(orders);
		for (int i = 0; i < ordersList.size(); i++) {
			// 转换目标用户图书借阅列表
			this.targetUserCommentedBooks.add(ordersList.get(i).getBooksid());
		}

		// 计算用户相似度，排序
		this.calcUserSimilarity(allUserBooks, booksList.size());
		// 计算图书推荐度，排序
		this.calcRecommendBooks(allUserBooks, booksList.size());
		// 处理推荐图书列表
		this.handleRecommendBooks(allUserBooks, booksList.size());
		String rommendId = "";
		for (int i = 0; i < this.targetRecommendBooks.size(); i++) {
			String item = this.targetRecommendBooks.get(i);
			if (!commentedBooks.contains(item)) {
				if (i == this.targetRecommendBooks.size() - 1) {
					rommendId += item;
				} else {
					rommendId += item + ",";
				}
			}
		}
		String[] str = rommendId.split(",");
		List<Books> bookList = new ArrayList<Books>();
		if (!"".equals(rommendId)) {
			for (String x : str) {
				System.out.println(x);
				Books g = this.booksDAO.getBooksById(x);
				bookList.add(g);
			}
		} else {
			bookList = this.booksDAO.getBooksByHot();
		}
		return bookList;
	}

	private void calcRecommendBooks(int[][] allUserBooks, int booksNum) {
		this.targetRecommendBooks = new ArrayList<String>();
		List<List<Object>> recommendBooks = new ArrayList<List<Object>>();
		List<Object> recommendBook = null;
		double recommdRate = 0, sumRate = 0;
		for (int i = 0; i < booksNum; i++) {
			recommendBook = new ArrayList<Object>();
			recommendBook.add(i);
			recommdRate = allUserBooks[Integer.parseInt(similarityUsers.get(0).get(0).toString())][i]
					* Double.parseDouble(similarityUsers.get(0).get(1).toString())
					+ allUserBooks[Integer.parseInt(similarityUsers.get(1).get(0).toString())][i]
							* Double.parseDouble(similarityUsers.get(1).get(1).toString());
			recommendBook.add(recommdRate);
			recommendBooks.add(recommendBook);
			sumRate += recommdRate;
		}
		System.out.println("sumRate  == > " + sumRate / booksNum);
		recommendBooks = compare(recommendBooks);
		for (List<Object> tList : recommendBooks) {
			System.out.println("推荐度为"+tList.get(1));
		}
		// 大于平均推荐度的图书才有可能被推荐
		for (int i = 0; i < recommendBooks.size(); i++) {
			List<Object> item = recommendBooks.get(i);
			if (Double.parseDouble(item.get(1).toString()) > sumRate / booksNum) { // 大于平均推荐度的图书才有可能被推荐
				System.out.println("books= = >" + books[Integer.parseInt(item.get(0).toString())]);
				this.targetRecommendBooks.add(books[Integer.parseInt(item.get(0).toString())]);
			}
		}
		for (String x : this.targetRecommendBooks) {
			System.out.println("x= = >" + x);
		}
	}

	/**
	 * 把推荐列表中用户已经借阅过的图书剔除
	 */
	private void handleRecommendBooks(int[][] allUserBooks, int booksNum) {
		int[] user2orders = new int[booksNum];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < booksNum; j++) {
				user2orders[j] = allUserBooks[i][j];
			}
		}
		commentedBooks = new ArrayList<String>();
		for (int i = 0; i < user2orders.length; i++) {
			if (allUserBooks[0][i] != 0) {
				commentedBooks.add(books[i]);
			}
		}
	}

	/**
	 * 获取两个最相似的用户
	 */
	private void calcUserSimilarity(int[][] allUserBooks, int booksNum) {
		int[] user2orders = new int[booksNum];
		List<List<Object>> tmpList = new ArrayList<List<Object>>();

		for (int i = 0; i < 10; i++) {
			if (i == targetUserIndex) {
				for (int j = 0; j < booksNum; j++) {
					user2orders[j] = allUserBooks[i][j];
				}
				continue;
			}
			List<Object> userSimilarity = new ArrayList<Object>();
			int[] user1orders = new int[booksNum];
			for (int j = 0; j < booksNum; j++) {
				user1orders[j] = allUserBooks[i][j];

			}
			userSimilarity.add(i);
			userSimilarity.add(calcTwoUserSimilarity(user1orders, user2orders, booksNum));
			tmpList.add(userSimilarity);
		}
		this.similarityUsers = compare(tmpList);
	}

	/**
	 * 根据用户数据，计算用户相似度
	 * 
	 * @param user1orders
	 * @param user2orders
	 * @return
	 */
	private static double calcTwoUserSimilarity(int[] user1orders, int[] user2orders, int booksNum) {
		double sum = 0;
		for (int i = 0; i < booksNum; i++) {
			sum += Math.pow(user1orders[i] - user2orders[i], 2);
		}
		return Math.sqrt(sum);
	}

	/**
	 * 集合排序
	 */
	private static List<List<Object>> compare(List<List<Object>> tmpList) {
		for (int i = 0; i < tmpList.size(); i++) {
			for (int j = 0; j < tmpList.size() - i; j++) {
				List<Object> t1 = tmpList.get(i);
				List<Object> t2 = tmpList.get(j);
				if (Double.parseDouble("" + t1.get(1)) > Double.parseDouble("" + t2.get(1))) {
					List<Object> tmp = new ArrayList<Object>();
					tmp = t1;
					tmpList.set(i, t2);
					tmpList.set(j, tmp);
				}
			}
		}
		return tmpList;
	}

}
