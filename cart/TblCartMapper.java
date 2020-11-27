package jp.co.internous.pancake.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import jp.co.internous.pancake.model.domain.TblCart;
import jp.co.internous.pancake.model.domain.dto.CartDto;

@Mapper
public interface TblCartMapper {

	//1 カート画面に遷移した際、出力内容としてtbl_cartとmst_productテーブルをJOINし、CartDtoに抽出するためのメソッド
	List<CartDto> findByUserId(@Param("userId") int userId);
	
	//2	3の処理結果が成功しなかった場合が存在した場合に、tbl_cartテーブルに新たにカート情報を登録するためのメソッド
	@Insert("INSERT INTO tbl_cart ("
			+ "user_id, product_id, product_count "
			+ ") "
			+ "VALUES ("
			+ "#{userId}, #{productId}, #{productCount} "
			+ ")")
	@Options(useGeneratedKeys=true, keyProperty="id")
	int insert(TblCart cart);
	
	//3	CartFormから渡されたuserIdに紐づくproductIdが存在する場合tbl_cart.product_countをcartFormから渡された個数を足して更新するメソッド
	@Update("UPDATE tbl_cart SET product_count = product_count + #{productCount}, updated_at = now() WHERE user_id = #{userId} AND product_id = #{productId}")
	int update(TblCart cart);
	
	//4	カート処理をする際に、insertかupdateかを判断する為にカート情報が存在するか確認するメソッド。
	@Select("SELECT count(id) FROM tbl_cart WHERE user_id = #{userId} AND product_id = #{productId}")
	int findCountByUserIdAndProuductId(@Param("userId") int userId, @Param("productId") int productId);
	
	//5 ログインした際に、仮ユーザーIDで登録されているカート情報があるか取得するメソッド
	@Select("SELECT count(user_id) FROM tbl_cart WHERE user_id = #{userId}")
	int findCountByUserId(@Param("userId") int userId);
	
	//6	ログインした際に、仮ユーザーIDに紐付くカート情報をユーザーIDに紐付け直すメソッド（仮ユーザーIDをユーザーIDにupdate）
	@Update("UPDATE tbl_cart SET user_id = #{userId}, updated_at = now() WHERE user_id = #{tmpUserId}")
	int updateUserId(@Param("userId") int userId, @Param("tmpUserId") int tmpUserId);
	
	//7	カート画面で削除ボタンが押された際に、選択されたidのカート情報を削除するメソッド
	@Delete("DELETE FROM tbl_cart WHERE id = #{id}")
	int deleteById(@Param("id") int id);

	//8 決済処理 2)でカート情報を削除するメソッド
	@Delete("DELETE FROM tbl_cart WHERE user_id = #{userId}")
	int deleteByUserId(@Param("userId") int userId);
}
