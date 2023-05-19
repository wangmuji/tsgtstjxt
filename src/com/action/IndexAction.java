package com.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.Article;
import com.entity.Books;
import com.entity.Cate;
import com.entity.Complains;
import com.entity.Orders;
import com.entity.Topic;
import com.entity.Users;
import com.service.ArticleService;
import com.service.BooksService;
import com.service.CateService;
import com.service.ComplainsService;
import com.service.OrdersService;
import com.service.RecommendService;
import com.service.TopicService;
import com.service.UsersService;
import com.util.PageHelper;
import com.util.VeDate;



//定义为控制器
@Controller
// 设置路径
@RequestMapping("/index")
public class IndexAction extends BaseAction {

	@Autowired
	@Resource
	private UsersService usersService;
	@Autowired
	@Resource
	private ArticleService articleService;
	@Autowired
	@Resource
	private CateService cateService;
	@Autowired
	@Resource
	private BooksService booksService;
	@Autowired
	@Resource
	private OrdersService ordersService;
	@Autowired
	@Resource
	private TopicService topicService;
	@Autowired
	@Resource
	private ComplainsService complainsService;
	@Autowired
	@Resource
	private RecommendService recommendService;

	// 公共方法 提供公共查询数据
	private void front() {
		this.getRequest().setAttribute("title", "图书馆图书推荐系统");
		List<Cate> cateList = this.cateService.getAllCate();
		this.getRequest().setAttribute("cateList", cateList);
		// 推荐图书的算法 1 用户未登录 直接调用booksService的getBooksByHot方法
		// 2 用户登录 但是没有借阅图书 则调用1
		// 3 用户登录 且有借阅的图书 调用recommendService 通过协同过滤算法 自动推荐图书
		// 理论上 用户越多 图书越多 借阅的人次 越多 推荐的就越准确
		if (this.getSession().getAttribute("userid") == null) {
			List<Books> hotList = this.booksService.getBooksByHot();
			this.getRequest().setAttribute("hotList", hotList);
		} else {
			String userid = (String) this.getSession().getAttribute("userid");
			System.out.println("当前用户为"+userid);
			Orders orders = new Orders();
			orders.setUsersid(userid);
			List<Orders> ordersList = this.ordersService.getOrdersByCond(orders);
			if (ordersList.size() == 0) {
				List<Books> hotList = this.booksService.getBooksByHot();
				this.getRequest().setAttribute("hotList", hotList);
			} else {
				List<Books> hotList = this.recommendService.getRecommend(userid);
				this.getRequest().setAttribute("hotList", hotList);
			}
		}
	}

	// 首页显示
	@RequestMapping("index.action")
	public String index() {
		this.front();
		List<Cate> cateList = this.cateService.getCateFront();
		List<Cate> frontList = new ArrayList<Cate>();
		for (Cate cate : cateList) {
			List<Books> booksList = this.booksService.getBooksByCate(cate.getCateid());
			cate.setBooksList(booksList);
			frontList.add(cate);
		}
		this.getRequest().setAttribute("frontList", frontList);
		return "users/index";
	}

	// 按分类查询
	@RequestMapping("cate.action")
	public String cate(String id, String number) {
		this.front();
		Books books = new Books();
		books.setCateid(id);
		List<Books> tempList = this.booksService.getBooksByCond(books);
		PageHelper.getIndexPage(tempList, "books", "cate", id, 12, number, this.getRequest());
		return "users/list";
	}

	// 全部产品
	@RequestMapping("all.action")
	public String all(String number) {
		this.front();
		List<Books> tempList = this.booksService.getAllBooks();
		PageHelper.getIndexPage(tempList, "books", "all", null, 12, number, this.getRequest());
		return "users/list";
	}
	//热门
	@RequestMapping("hot.action")
	public String hot(String number) {
		this.front();
		List<Books> hotList=null;
		if (this.getSession().getAttribute("userid") == null) {
			hotList = this.booksService.getBooksByHot();
			this.getRequest().setAttribute("hotList", hotList);
		} else {
			String userid = (String) this.getSession().getAttribute("userid");
			System.out.println("当前用户为"+userid);
			Orders orders = new Orders();
			orders.setUsersid(userid);
			List<Orders> ordersList = this.ordersService.getOrdersByCond(orders);
			if (ordersList.size() == 0) {
				hotList = this.booksService.getBooksByHot();
				this.getRequest().setAttribute("hotList", hotList);
			} else {
				 hotList = this.recommendService.getRecommend(userid);
				this.getRequest().setAttribute("hotList", hotList);
			}
		}
		PageHelper.getIndexPage(hotList, "books", "all", null, 12, number, this.getRequest());
		return "users/list";
	}
	// 查询商品
	@RequestMapping("query.action")
	public String query(String name) {
		this.front();
		Books books = new Books();
		books.setBooksname(name);
		List<Books> booksList = this.booksService.getBooksByLike(books);
		this.getRequest().setAttribute("booksList", booksList);
		return "users/list";
	}

	@RequestMapping("detail.action")
	public String detail(String id) {
		this.front();
		Books books = this.booksService.getBooksById(id);
		this.getRequest().setAttribute("books", books);
		Topic topic = new Topic();
		topic.setBooksid(id);
		List<Topic> topicList = this.topicService.getTopicByCond(topic);
		this.getRequest().setAttribute("topicList", topicList);
		this.getRequest().setAttribute("tnum", topicList.size());
		return "users/detail";
	}

	// 公告
	@RequestMapping("article.action")
	public String article(String number) {
		this.front();
		List<Article> tempList = this.articleService.getAllArticle();
		PageHelper.getIndexPage(tempList, "article", "article", null, 10, number, this.getRequest());
		return "users/article";
	}

	// 阅读公告
	@RequestMapping("read.action")
	public String read(String id) {
		this.front();
		Article article = this.articleService.getArticleById(id);
		article.setHits("" + (Integer.parseInt(article.getHits()) + 1));
		this.articleService.updateArticle(article);
		this.getRequest().setAttribute("article", article);
		return "users/read";
	}

	// 准备登录
	@RequestMapping("preLogin.action")
	public String prelogin() {
		this.front();
		return "users/login";
	}

	// 用户登录
	@RequestMapping("login.action")
	public String login() {
		this.front();
		String username = this.getRequest().getParameter("username");
		String password = this.getRequest().getParameter("password");
		Users u = new Users();
		u.setUsername(username);
		List<Users> usersList = this.usersService.getUsersByCond(u);
		if (usersList.size() == 0) {
			this.getSession().setAttribute("message", "用户名不存在");
			return "redirect:/index/preLogin.action";
		} else {
			Users users = usersList.get(0);
			if (password.equals(users.getPassword())) {
				this.getSession().setAttribute("userid", users.getUsersid());
				this.getSession().setAttribute("username", users.getUsername());
				this.getSession().setAttribute("users", users);
				return "redirect:/index/index.action";
			} else {
				this.getSession().setAttribute("message", "密码错误");
				return "redirect:/index/preLogin.action";
			}
		}
	}

	// 准备注册
	@RequestMapping("preReg.action")
	public String preReg() {
		this.front();
		return "users/register";
	}

	// 用户注册
	@RequestMapping("register.action")
	public String register(Users users) {
		this.front();
		Users u = new Users();
		u.setUsername(users.getUsername());
		List<Users> usersList = this.usersService.getUsersByCond(u);
		if (usersList.size() == 0) {
			users.setRegdate(VeDate.getStringDateShort());
			this.usersService.insertUsers(users);
		} else {
			this.getSession().setAttribute("message", "用户名已存在");
			return "redirect:/index/preReg.action";
		}

		return "redirect:/index/preLogin.action";
	}

	// 退出登录
	@RequestMapping("exit.action")
	public String exit() {
		this.front();
		this.getSession().removeAttribute("userid");
		this.getSession().removeAttribute("username");
		this.getSession().removeAttribute("users");
		return "index";
	}

	// 准备修改密码
	@RequestMapping("prePwd.action")
	public String prePwd() {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		return "users/editpwd";
	}

	// 修改密码
	@RequestMapping("editpwd.action")
	public String editpwd() {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		String userid = (String) this.getSession().getAttribute("userid");
		String password = this.getRequest().getParameter("password");
		String repassword = this.getRequest().getParameter("repassword");
		Users users = this.usersService.getUsersById(userid);
		if (password.equals(users.getPassword())) {
			users.setPassword(repassword);
			this.usersService.updateUsers(users);
		} else {
			this.getSession().setAttribute("message", "旧密码错误");
			return "redirect:/index/prePwd.action";
		}
		return "redirect:/index/prePwd.action";
	}

	@RequestMapping("usercenter.action")
	public String usercenter() {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		return "users/usercenter";
	}

	@RequestMapping("userinfo.action")
	public String userinfo() {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		String userid = (String) this.getSession().getAttribute("userid");
		this.getSession().setAttribute("users", this.usersService.getUsersById(userid));
		return "users/userinfo";
	}

	@RequestMapping("personal.action")
	public String personal(Users users) {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		this.usersService.updateUsers(users);
		return "redirect:/index/userinfo.action";
	}

	@RequestMapping("preComplains.action")
	public String preComplains() {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		return "users/addComplains";
	}

	@RequestMapping("addComplains.action")
	public String addComplains(Complains complains) {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		String userid = (String) this.getSession().getAttribute("userid");
		complains.setAddtime(VeDate.getStringDateShort());
		complains.setStatus("未回复");
		complains.setUsersid(userid);
		this.complainsService.insertComplains(complains);
		return "redirect:/index/myComplains.action";
	}

	@RequestMapping("myComplains.action")
	public String myComplains(String number) {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		String userid = (String) this.getSession().getAttribute("userid");
		Complains complains = new Complains();
		complains.setUsersid(userid);
		List<Complains> list = this.complainsService.getComplainsByCond(complains);
		PageHelper.getIndexPage(list, "complains", "myComplains", null, 10, number, this.getRequest());
		return "users/myComplains";
	}

	@RequestMapping("addorder.action")
	public String addorder(String id) {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		String userid = (String) this.getSession().getAttribute("userid");
		Orders orders = new Orders();
		orders.setBooksid(id);
		orders.setOrdercode("BD" + VeDate.getStringDatex());
		orders.setOrderdate(VeDate.getStringDateShort());
		orders.setStatus("预约");
		orders.setUsersid(userid);
		orders.setThestart("-");
		orders.setTheend("-");
		this.ordersService.insertOrders(orders);
		return "redirect:/index/showOrders.action";
	}

	@RequestMapping("showOrders.action")
	public String showOrders(String number) {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		String userid = (String) this.getSession().getAttribute("userid");
		Orders x = new Orders();
		x.setUsersid(userid);
		List<Orders> ordersList = this.ordersService.getOrdersByCond(x);
		List<Orders> list = new ArrayList<Orders>();
		for (Orders orders : ordersList) {
			if (!"-".equals(orders.getThestart()) && "借阅".equals(orders.getStatus())) {
				long days = VeDate.getDays(VeDate.getStringDateShort(), orders.getThestart()) + 1;
				if (days > 15) {
					orders.setMemo("<font color='red'>已超期</font>");
				} else if (15 - days <= 3) {
					orders.setMemo(
							"<font color='red'>即将超期</font>&nbsp;|&nbsp;<a href='index/continu.action?id=" + orders.getOrdersid() + "'>续借</a>");
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

		PageHelper.getIndexPage(ordersList, "orders", "showOrders", null, 10, number, this.getRequest());
		return "users/orderlist";
	}


	@RequestMapping("continu.action")
	public String continu(String id) {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		Orders x = this.ordersService.getOrdersById(id);
		x.setStatus("已续借");
		x.setTheend(VeDate.getStringDateShort());
		this.ordersService.updateOrders(x);
		Orders orders = new Orders();
		orders.setBooksid(x.getBooksid());
		orders.setOrdercode("BD" + VeDate.getStringDatex());
		orders.setOrderdate("-");
		orders.setStatus("续借");
		orders.setUsersid(x.getUsersid());
		orders.setThestart(VeDate.getStringDateShort());
		orders.setTheend("-");
		this.ordersService.insertOrders(orders);
		return "redirect:/index/showOrders.action";
	}

	@RequestMapping("cancel.action")
	public String cancel(String id) {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		Orders orders = this.ordersService.getOrdersById(id);
		orders.setStatus("取消");
		this.ordersService.updateOrders(orders);
		return "redirect:/index/showOrders.action";
	}


	@RequestMapping("preTopic.action")
	public String preTopic(String id) {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		this.getRequest().setAttribute("id", id);
		return "users/addTopic";
	}

	@RequestMapping("addTopic.action")
	public String addTopic(String id) {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		Orders orders = this.ordersService.getOrdersById(this.getRequest().getParameter("id"));
		orders.setStatus("已评价");
		this.ordersService.updateOrders(orders);
		Topic topic = new Topic();
		String userid = (String) this.getSession().getAttribute("userid");
		topic.setAddtime(VeDate.getStringDate());
		topic.setBooksid(orders.getBooksid());
		topic.setContents(this.getRequest().getParameter("contents"));
		topic.setNum(this.getRequest().getParameter("num"));
		topic.setUsersid(userid);
		this.topicService.insertTopic(topic);
		return "redirect:/index/showOrders.action";
	}

}
