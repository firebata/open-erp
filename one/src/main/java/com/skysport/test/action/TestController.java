package com.skysport.test.action;

import com.skysport.test.model.TestModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Scope("prototype")
@Controller
@RequestMapping("/test")
public class TestController {
	/**
	 * 测试方法,HelloWord
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public ModelAndView getProducts(HttpServletRequest request,
			HttpServletResponse response)  {

		request.setAttribute("name", "张三");
		Map<String, Object> map = new HashMap<String, Object>();
		TestModel pro = new TestModel();
		pro.setPid("s101");
		pro.setPname("张三");
		map.put("m1", "001");
		map.put("pro", pro);

		ModelAndView mav = new ModelAndView("test/list", map);
		return mav;

	}

	@RequestMapping(value = "/info/{proId}", method = RequestMethod.GET)
	public String getProductInfo(@PathVariable String proId,
			HttpServletRequest request, HttpServletResponse response)
			 {

		request.setAttribute("name", proId);

		return "test/list";

	}

	@RequestMapping(value = "/info/{pid}/{pname}", method = RequestMethod.GET)
	public String getProductInfo(TestModel pro, HttpServletRequest request,
			HttpServletResponse response)  {

		request.setAttribute("name", pro.getPid() + "喜欢" + pro.getPname());

		return "test/list";

	}

	@RequestMapping(value = "/info")
	@ResponseBody
	public ModelAndView insertProduct(TestModel pro,
			HttpServletRequest request, HttpServletResponse response)
			 {

		request.setAttribute("name", pro.getPid() + "|" + pro.getPname());
		ModelAndView mav = new ModelAndView("test/list");
		return mav;

	}

	@RequestMapping(value = "/infoJson")
	@ResponseBody
	public ModelAndView updateProduct(@RequestBody TestModel pro,
			HttpServletRequest request, HttpServletResponse response)
			 {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("success", "true");
		ModelAndView mav = new ModelAndView("test/list", map);
		return mav;
	}

	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, HttpServletResponse response)
			 {
		return "test/add";

	}

	@RequestMapping(value = { "/info2/{pid}", "/info2/{pid}/{pname}.json" }, method = RequestMethod.GET)
	public ModelAndView getProductInfos(@PathVariable String pid,
			@PathVariable String pname, HttpServletRequest request,
			HttpServletResponse response)  {

		Map<String, Object> map = new HashMap<String, Object>();

		TestModel pro = new TestModel();
		pro.setPid(pid);
		pro.setPname(pname);
		map.put("name", pname);
		map.put("pro", pro);

		ModelAndView mav = new ModelAndView("test/list", map);

		return mav;
	}

}
