package coupon.controller;

import common.controller.AbstractController;
import coupon.model.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ReceiptCoupon extends AbstractController {

	private CouponDAO cdao = new CouponDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		//int n = cdao.reciptCoupon();
		
	}

}
