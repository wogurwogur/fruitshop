/**
 * 
 */

$(()=>{
	
	$("input#checkAll").click(e=>{
		
		if($("input#checkAll").prop('checked')) {
			$("input:checkbox[name='select']").prop('checked', true);
		}
		else {
			$("input:checkbox[name='select']").prop('checked', false);
		}
		
	});
	
	$("input:checkbox[name='select']").on('click', e =>{
		
		let total_length = $('input:checkbox[name="select"]').length;
		let checked_length = $('input:checkbox[name="select"]:checked').length;
		
		if(checked_length == total_length) {
			$("input#checkAll").prop('checked', true);
		}
		else {
			$("input#checkAll").prop('checked', false);
		}

	});
	
	
	$("button#shipAdd").click(()=>{
		location.href="shipAdd.ddg";
	});
	
	
	$("input:radio[name='default']").change(e=>{
		
		const ship_no = $(e.target).val();
		
		const frm = document.shipInfoFrm;
		frm.action = "shipDefault.ddg";
		frm.inputValue.value = ship_no;
		frm.submit();
		
	});
	
	
	
	
});


function goUpdate(ship_no) {
	
	
	const frm = document.shipInfoFrm;
	
	frm.action = "shipUpdate.ddg";
	frm.method = "get";
	frm.inputValue.value = ship_no;
	frm.submit();
}


function goDelete(ship_no) {
	
	if(confirm("정말로 삭제 하시겠습니까?")) {
		
		const frm = document.shipInfoFrm;
			
		frm.action = "shipDelete.ddg";
		frm.method = "post";
		frm.inputValue.value = ship_no;
		frm.submit();
	}
}


function goCheckDelete() {
		
	if($("input:checkbox[name='select']:checked").length > 0 ) {
				
		if(confirm("정말로 삭제 하시겠습니까?")) {
			let arr_ship_no = new Array();
					
			$("input:checkbox[name='select']:checked").each( (index, element) => {
						
				arr_ship_no.push(element.value);
						
			});
					
			const str_ship_no = arr_ship_no.toString();
					
			console.log(str_ship_no);
					
					
			const frm = document.shipInfoFrm;
					
			frm.action = "shipDelete.ddg";
			frm.inputValue.value = str_ship_no;
			frm.method="post";
			frm.submit();
		}
	}
}


function goUpdate(ship_no) {
	
	const frm = document.shipInfoFrm;
				
	frm.action = "shipUpdate.ddg";
	frm.inputValue.value = ship_no;
	frm.submit();

}





