<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- DataTables CSS -->
<link href="<c:url value="/assets/libs/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css"/>" rel="stylesheet">

<!-- DataTables Responsive CSS -->
<link href="<c:url value="/assets/libs/datatables-responsive/css/dataTables.responsive.css" />" rel="stylesheet">

<div id="${(disableHeader != null) ? 'page-wrapper-no-layout' : 'page-wrapper'}">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">Tạo danh sách thành viên hội đồng</h1>
			<button type="button" class='form btn btn-primary' id="cancel" style="margin-top:-5px; margin-bottom:10px;" >Quay về</button>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<form:form id="form-add" action="${baseUrl}/mm/save-a-jury-members.html" method="POST" role="form" accept-charset="UTF-8">
		<div class="row">
			<div class="col-lg-6">
				<div class="well">
					<h4>Đợt bảo vệ</h4>
						<p class="value">${defenseSession.DEFSESS_Name}</p>
						<input id="defenseSession" type="hidden" value="${defenseSession.DEFSESS_Code}">					  
				
				</div>
				<!-- <div class="well">
					<h4>Danh sách giảng viên</h4>
					<c:if test="${listMembers != null}">
						<select class="form-control" name="juryMember" id="juryMember" >
							<c:forEach items="${listMembers}" var="aMember">
			               		<option value="${aMember.staff_Code}" >${aMember.staff_Name}</option>
			               	</c:forEach>
                    	</select>
                    </c:if>   				
				</div>-->
				
				<!-- /.col-lg-4 -->
			</div>
			<div class="col-lg-6">
				<div class="well">
					<h4>Bộ môn</h4>
					<p class="value">${department}</p>                    
				</div>
			</div>
	
		</div>
		<!-- /.row -->
		
		<div class="panel panel-default">
				<div class="panel-heading"><h4>Chọn giảng viên tham gia vào hội đồng</h4> 
				    
				</div>
					
				<!-- /.panel-heading -->
				<div class="panel-body">
					<div class="col-md-6">
						<div class="dataTable_wrapper">
						<div class="" id="message">
							
						</div>
						<h4>Danh sách giảng viên chưa có trong hội đồng</h4>
						<table class="table table-striped table-bordered table-hover"
							id="dataTables-1">
							<thead>
								<tr>
									<th title="Name">Họ và tên</th>
									<th title="Department">Bộ môn</th>
									<th title="University">Trường</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${listMembers != null}">
									<c:forEach items="${listMembers}" var="aMember">															
										<tr class="gradeX">
										  	<td><c:out value="${aMember.staff_Name}"/></td>
										
										 	<td><c:out value="${aMember.department.department_Name}"/></td>
										 	<td><c:out value="${aMember.department.faculty.university.university_Name}"/></td>
										
										 	<td class="center"><button type="button" onclick="addJuryMember('${aMember.staff_Code}');" class="btn btn-info btn-xs" title="Remove">Thêm</button></td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
					</div>
					<!-- /.table-responsive -->
					</div>
					
					<div class="col-md-6">
						<div class="dataTable_wrapper">
						<div class="" id="message">
							
						</div>
						<h4>Danh sách giảng viên đã có trong hội đồng</h4>
						<table class="table table-striped table-bordered table-hover"
							id="dataTables-2">
							<thead>
								<tr>
									<th title="Name">Họ và tên</th>
									<th title="Department">Bộ môn</th>
									<th title="University">Trường</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${listJuryMembers != null}">
									<c:forEach items="${listJuryMembers}" var="aJuryMember">															
										<tr class="gradeX">
										  	<td><c:out value="${aJuryMember.memJuryMember.staff_Name}"/></td>
										
										 	<td><c:out value="${aJuryMember.memJuryMember.department.department_Name}"/></td>
										 	<td><c:out value="${aJuryMember.memJuryMember.department.faculty.university.university_Name}"/></td>
										
										 	<td class="center"><button type="button" onclick="v_fRemoveJuryMember('${aJuryMember.juryMem_Code}');" class="btn btn-danger btn-xs" title="Remove">Loại bỏ</button></td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
					</div>
					<!-- /.table-responsive -->
					</div>

				</div>
			</div>
		
		
		
		
		
		<!--<div class="panel panel-default">
									
				<div class="panel-body">
					<div class="dataTable_wrapper">
						<div class="" id="message">
							
						</div>
						<table class="table table-striped table-bordered table-hover"
							id="dataTables-professor">
							<thead>
								<tr>
									<th title="Name">Tên Giảng viên</th>
									<th title="Session">Đợt bảo vệ</th>
									<th title="Remove">Remove</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${listJuryMembers != null}">
									<c:forEach items="${listJuryMembers}" var="aJuryMember">															
										<tr class="gradeX">
										 	<td><c:out value="${aJuryMember.memJuryMember.staff_Name}"/></td>
										 	<td><c:out value="${aJuryMember.defenseSessionJuryMember.DEFSESS_Name}"/></td>
										 	<td class="center"><button type="button" onclick="v_fRemoveJuryMember('${aJuryMember.juryMem_Code}');" class="btn btn-danger btn-xs" title="Remove">Remove</button></td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
					</div>
			
				</div>
			</div>
			-->
		
		<!-- <div class="row" style="padding-bottom:20px;">
			<div class="col-lg-4">
				<button type="button" class='form btn btn-primary' id="cancel" >Quay về</button>				
			</div>
			
		</div>-->
		
		<!-- /.row -->
	</form:form>
</div>
<!-- /#page-wrapper -->

<!-- DataTables JavaScript -->
<script
	src="<c:url value="/assets/libs/datatables/media/js/jquery.dataTables.js"/>"></script>
<script
	src="<c:url value="/assets/libs/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.js"/>"></script>
	

<!-- Page-Level Demo Scripts - Tables - Use for reference -->
<script type="text/javascript">
	$(document).ready(function() {
		$('#cancel').click(function() {
			window.location = baseUrl + "/mm/defensesession.html";
		});	
		
	});

	 $('#dataTables-1').DataTable({
         responsive: true,
         "aoColumnDefs": [
                          { 'bSortable': false, 'aTargets': [3] }
                       ]
	 });
	 
	 $('#dataTables-2').DataTable({
         responsive: true,
         "aoColumnDefs": [
                          { 'bSortable': false, 'aTargets': [3] }
                       ]
	 });
	
	function addJuryMember(sJuryMemberCode)
	{
		//var sJuryMemberCode = $("select#juryMember option:selected").val();
		var sDefenseSessionCode = $("#defenseSession").val();
		var savingJuryMemberUrl = "${baseUrl}/mmservice/savejurymember.html";
		if (sJuryMemberCode.length > 0 ) {
			 $.ajax({
					type: "POST",
					url: savingJuryMemberUrl,
					data: "sJuryMemberCode="+sJuryMemberCode+"&sDefenseSessionCode="+sDefenseSessionCode,
					cache: true,
					beforeSend: function () { 
						//$('#department').html('<img src="loader.gif" alt="" width="24" height="24">');
					},
					success: function(html) {    
						$("#status").html( html );
						window.location = baseUrl + "/mm/jurymembers/"+${defenseSession.DEFSESS_ID}+".html";
					}
				});
		}else{
			
		}
	}
	
	function v_fRemoveJuryMember(sJuryMemberCode){
		//var sJuryMemberCode = $("select#juryMember option:selected").val();
		var sDefenseSessionCode = $("#defenseSession").val();
		var savingJuryMemberUrl = "${baseUrl}/mmservice/removejurymember.html";
		if (sJuryMemberCode.length > 0 ) {
			 $.ajax({
					type: "POST",
					url: savingJuryMemberUrl,
					data: "sJuryMemberCode="+sJuryMemberCode+"&sDefenseSessionCode="+sDefenseSessionCode,
					cache: true,
					beforeSend: function () { 
						//$('#department').html('<img src="loader.gif" alt="" width="24" height="24">');
					},
					success: function(html) {    
						$("#status").html( html );
						window.location = baseUrl + "/mm/jurymembers/"+${defenseSession.DEFSESS_ID}+".html";
					}
				});
		}else{
			
		}
	}
	
</script>