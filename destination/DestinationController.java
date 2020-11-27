package jp.co.internous.pancake.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import jp.co.internous.pancake.model.domain.MstDestination;
import jp.co.internous.pancake.model.domain.MstUser;
import jp.co.internous.pancake.model.form.DestinationForm;
import jp.co.internous.pancake.model.mapper.MstDestinationMapper;
import jp.co.internous.pancake.model.mapper.MstUserMapper;
import jp.co.internous.pancake.model.session.LoginSession;

@Controller
@RequestMapping("/pancake/destination")
public class DestinationController {
	
	@Autowired
	private MstUserMapper userMapper;
	
	@Autowired
	private LoginSession loginSession;
	
	@Autowired
	private MstDestinationMapper destinationMapper;
	
	private Gson gson = new Gson();
	
	@RequestMapping("/")
	public String index(Model m) {
		//ユーザー情報とログイン情報を取得、初期表示画面
		MstUser user = userMapper.findByUserNameAndPassword(loginSession.getUserName(), loginSession.getPassword());
		
		m.addAttribute("user", user);
		m.addAttribute("loginSession",loginSession);
		
		return "destination";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/delete")
	@ResponseBody
	public boolean delete(@RequestBody String destinationId) {
		//決済画面の宛先削除機能
		Map<String, String> map = gson.fromJson(destinationId, Map.class);
		String id = map.get("destinationId");
		
		int result = destinationMapper.logicalDeleteById(Integer.parseInt(id));
		
		return result >0;
	}
	
	@RequestMapping("/register")
	@ResponseBody
	public String register(@RequestBody DestinationForm f) {
		//宛先登録
		MstDestination destination = new MstDestination(f);
		int userId = loginSession.getUserId();
		destination.setUserId(userId);
		int count = destinationMapper.insert(destination);
		
		// 登録した宛先のIDを取得(Stringで返すために、intではなくIntegerを使い、toStringメソッドでStringに変換）
		Integer id = 0;
		if (count > 0) {
			id = destination.getId();
		}
		return id.toString();
	}
}
