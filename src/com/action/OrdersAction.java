package com.action;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.entity.Orders;
import com.service.OrdersService;
import com.entity.Users;
import com.entity.Books;
import com.service.UsersService;
import com.service.BooksService;
import com.util.PageHelper;
import com.util.VeDate;

//定义为控制器
@Controller
// 设置路径
@RequestMapping(value = "/orders", produces = "text/plain;charset=utf-8")
public class OrdersAction extends BaseAction {
	// 注入Service 由于标签的存在 所以不需要getter setter
	@Autowired
	@Resource
	private OrdersService ordersService;
	@Autowired
	@Resource
	private UsersService usersService;
	@Autowired
	@Resource
	private BooksService booksService;

	// 准备添加数据
	@RequestMapping("createOrders.action")
	public String createOrders() {
		List<Users> usersList = this.usersService.getAllUsers();
		this.getRequest().setAttribute("usersList", usersList);
		List<Books> booksList = this.booksService.getAllBooks();
		this.getRequest().setAttribute("booksList", booksList);
		return "admin/addorders";
	}

	// 添加数据
	@RequestMapping("addOrders.action")
	public String addOrders(Orders orders) {
		this.ordersService.insertOrders(orders);
		return "redirect:/orders/createOrders.action";
	}

	// 通过主键删除数据
	@RequestMapping("deleteOrders.action")
	public String deleteOrders(String id) {
		this.ordersService.deleteOrders(id);
		return "redirect:/orders/getAllOrders.action";
	}

	// 批量删除数据
	@RequestMapping("deleteOrdersByIds.action")
	public String deleteOrdersByIds() {
		String[] ids = this.getRequest().getParameterValues("ordersid");
		for (String ordersid : ids) {
			this.ordersService.deleteOrders(ordersid);
		}
		return "redirect:/orders/getAllOrders.action";
	}

	// 更新数据
	@RequestMapping("updateOrders.action")
	public String updateOrders(Orders orders) {
		this.ordersService.updateOrders(orders);
		return "redirect:/orders/getAllOrders.action";
	}

	@RequestMapping("lend.action")
	public String lend(String id) {
		Orders orders = this.ordersService.getOrdersById(id);
		Orders x = new Orders();
		x.setUsersid(orders.getUsersid());
		x.setStatus("借阅");
		List<Orders> xList = this.ordersService.getOrdersByCond(x);
		if (xList.size() >= 5) {
			this.getSession().setAttribute("message", "已经借阅5本 , 请先归还图书");
			return "redirect:/orders/getAllOrders.action";
		}
		Books books = this.booksService.getBooksById(orders.getBooksid());
		if ("0".equals(books.getLendnum())) {
			this.getSession().setAttribute("message", "库存错误");
		} else {
			books.setStorage("" + (Integer.parseInt(books.getStorage()) - 1));
			books.setLendnum("" + (Integer.parseInt(books.getLendnum()) - 1));
			this.booksService.updateBooks(books);
			orders.setStatus("借阅");
			orders.setThestart(VeDate.getStringDateShort());
			this.ordersService.updateOrders(orders);
		}
		return "redirect:/orders/getAllOrders.action";
	}

	@RequestMapping("back.action")
	public String back(String id) {
		Orders orders = this.ordersService.getOrdersById(id);
		Books books = this.booksService.getBooksById(orders.getBooksid());
		books.setStorage("" + (Integer.parseInt(books.getStorage()) + 1));
		books.setLendnum("" + (Integer.parseInt(books.getLendnum()) + 1));
		this.booksService.updateBooks(books);
		orders.setStatus("归还");
		orders.setTheend(VeDate.getStringDateShort());
		this.ordersService.updateOrders(orders);
		return "redirect:/orders/getAllOrders.action";
	}

	// 显示全部数据
	@RequestMapping("getAllOrders.action")
	public String getAllOrders(String number) {
		List<Orders> ordersList = this.ordersService.getAllOrders();
		List<Orders> list = new ArrayList<Orders>();
		for (Orders orders : ordersList) {
			if (!"-".equals(orders.getThestart()) && "借阅".equals(orders.getStatus())) {
				long days = VeDate.getDays(VeDate.getStringDateShort(), orders.getThestart()) + 1;
				if (days > 15) {
					orders.setMemo("<font color='red'>已超期</font>");
				} else if (15 - days <= 3) {
					orders.setMemo("<font color='red'>即将超期</font>");
				}
				list.add(orders);
			} else if (!"-".equals(orders.getThestart()) && "续借".equals(orders.getStatus())) {
				long days = VeDate.getDays(VeDate.getStringDateShort(), orders.getThestart()) + 1;
				if (days > 15) {
					orders.setMemo("<font color='red'>已超期</font>");
				} else if (15 - days <= 3) {
					orders.setMemo("<font color='red'>即将超期</font>");
				}
				list.add(orders);
			} else {
				list.add(orders);
			}
		}
		PageHelper.getPage(list, "orders", null, null, 10, number, this.getRequest(), null);
		return "admin/listorders";
	}

	// 按条件查询数据 (模糊查询)
	@RequestMapping("queryOrdersByCond.action")
	public String queryOrdersByCond(String cond, String name, String number) {
		Orders orders = new Orders();
		if (cond != null) {
			if ("ordercode".equals(cond)) {
				orders.setOrdercode(name);
			}
			if ("usersid".equals(cond)) {
				orders.setUsersid(name);
			}
			if ("booksid".equals(cond)) {
				orders.setBooksid(name);
			}
			if ("status".equals(cond)) {
				orders.setStatus(name);
			}
			if ("orderdate".equals(cond)) {
				orders.setOrderdate(name);
			}
			if ("thestart".equals(cond)) {
				orders.setThestart(name);
			}
			if ("theend".equals(cond)) {
				orders.setTheend(name);
			}
		}

		List<String> nameList = new ArrayList<String>();
		List<String> valueList = new ArrayList<String>();
		nameList.add(cond);
		valueList.add(name);
		PageHelper.getPage(this.ordersService.getOrdersByLike(orders), "orders", nameList, valueList, 10, number, this.getRequest(), "query");
		name = null;
		cond = null;
		return "admin/queryorders";
	}

	// 按主键查询数据
	@RequestMapping("getOrdersById.action")
	public String getOrdersById(String id) {
		Orders orders = this.ordersService.getOrdersById(id);
		this.getRequest().setAttribute("orders", orders);
		List<Users> usersList = this.usersService.getAllUsers();
		this.getRequest().setAttribute("usersList", usersList);
		List<Books> booksList = this.booksService.getAllBooks();
		this.getRequest().setAttribute("booksList", booksList);
		return "admin/editorders";
	}

	public OrdersService getOrdersService() {
		return ordersService;
	}

	public void setOrdersService(OrdersService ordersService) {
		this.ordersService = ordersService;
	}

}

