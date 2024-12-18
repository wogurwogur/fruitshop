/**
 * 
 */
$(document).ready(function(){
	
	$("select[name='select_sort']").bind("change", function(e){
		
		const select_sort = $(e.target).val();
		
		console.log(select_sort);
		
		const frm = document.memberSerchSort;
		
		frm.select_sort.value = select_sort;
		frm.method = "post"
		frm.action="/admin/adminManagement.ddg";
		
		/**-------------------------------------------------------------------------------------------------**/
		// 여기하는중
		/**-------------------------------------------------------------------------------------------------**/
		
	});
	
});


