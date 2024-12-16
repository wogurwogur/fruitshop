package cart.controller;


import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CartList extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		

		/*
		  request.setAttribute("cartItems", cartItems);
		  request.setAttribute("totalPrice", totalPrice);
		  request.setAttribute("배송비", 배송비);
		 */
		
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/cart/cartList.jsp");

		
		
	}

}
