package jp.co.internous.pancake.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import jp.co.internous.pancake.model.domain.dto.CartDto;
import jp.co.internous.pancake.model.mapper.TblCartMapper;
import jp.co.internous.pancake.model.session.LoginSession;
import jp.co.internous.pancake.model.domain.TblCart;
import jp.co.internous.pancake.model.form.CartForm;

@Controller
@RequestMapping("/pancake/cart")
public class CartController {

	@Autowired
	private LoginSession loginSession;

	@Autowired
	private TblCartMapper tblCartMapper;

	Gson gson = new Gson();

	// 初期表示
	@RequestMapping("/")
	public String index(Model m) {
		// ユーザーIDを取得
		int userId;
		if (loginSession.getLogined() == true) {
			userId = loginSession.getUserId();
		} else {
			userId = loginSession.getTmpUserId();
		}
		// カート情報を取得
		List<CartDto> carts = tblCartMapper.findByUserId(userId);
		m.addAttribute("loginSession", loginSession);
		m.addAttribute("carts", carts);

		return "cart";
	}
	
	//カート追加処理
	@RequestMapping("/add")
	public String addCart(CartForm f, Model m) {
		// ユーザーIDを取得
		int userId;
		if (loginSession.getLogined() == true) {
			userId = loginSession.getUserId();
		} else {
			userId = loginSession.getTmpUserId();
		}
		f.setUserId(userId);
		
		//　カートテーブルに挿入or更新
		TblCart cart = new TblCart(f);
		int result = 0;
		if (tblCartMapper.findCountByUserIdAndProuductId(userId, f.getProductId()) > 0) {
			result = tblCartMapper.update(cart);
		} else {
			result = tblCartMapper.insert(cart);
		}
		if (result > 0) {
			List<CartDto> carts = tblCartMapper.findByUserId(userId);
			m.addAttribute("loginSession",loginSession);
			m.addAttribute("carts",carts);
		}		
		
		return "cart";
		
	}

	// カート削除機能
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/delete")
	public boolean deleteCart(@RequestBody String checkedIdList) {
		int result = 0;
		
		Map<String, List<String>> map = gson.fromJson(checkedIdList, Map.class);
		List<String> checkedIds = map.get("checkedIdList");
		for (String id : checkedIds) {
			result += tblCartMapper.deleteById(Integer.parseInt(id));
		}
		
		return result > 0;
	}
}