<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- DataTables CSS -->
<link href="<c:url value="/assets/libs/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css"/>" rel="stylesheet">

<!-- DataTables Responsive CSS -->
<link href="<c:url value="/assets/libs/datatables-responsive/css/dataTables.responsive.css" />" rel="stylesheet">

<!-- Custom CSS -->
<link href="<c:url value="/assets/css/sb-admin-2.css" />" rel="stylesheet" type="text/css" media="all" />

	   
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">Thêm một thành viên cho hội đồng tuyển chọn đợt gọi đề tài</h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Thêm mới một thành viên cho hội đồng tuyển chọn đợt gọi đề tài 
                </div>
                <div class="panel-body">
                	<c:if test="${status != null}">
	                	<div class="alert alert-success">
	                	
	                        ${status}. <a href="<c:url value="${baseUrl}/cp/add-a-jury-member-for-an-announced-project-call.html"/>" class="alert-link">Trở lại</a>.
	                    </div>
                    </c:if>
                    <c:if test="${err != null}">
	                	<div class="alert alert-warning">${err}</div>
                    </c:if>     
                    <form:form action="${baseUrl}/cp/save-jury-of-announced-project-call.html" method="POST" commandName="juryOfAnnouncedProjectCallFormAdd" role="form" >
	                    <div class="row">
	                        <div class="col-lg-12">
	                        
	                                <div class="form-group">
	                                    <label>Đợt gọi đề tài </label>
	                                    <form:select path="JUSUPRJ_PRJCALLCODE" class="form-control" name="JUSUPRJ_PRJCALLCODE">
	                                        <option value="">chọn</option>	                                    	        	                                    	
	                                    	<c:forEach items="${projectCallList}" var="iProjectCall">
		                                        <option value="${iProjectCall.PROJCALL_CODE}">${iProjectCall.PROJCALL_NAME}</option>
	                                       	</c:forEach>
	                                    </form:select>
	                                    <form:errors path="JUSUPRJ_PRJCALLCODE" class="alert-danger"></form:errors>
	                                </div>
	                        
	                                <div class="form-group">
	                                	
	                                	<select class="form-control" name="faculty" onchange="showDepartment(this);" >
                                 		<option value="">Chọn Khoa/Viện</option>
                                 		<c:forEach items="${listFaculty}" var="faculty">
                                      	<option value="${faculty.faculty_Code}">${faculty.faculty_Name}</option>
                                    	</c:forEach>
                                 		</select>
                                    	<br>
                                    	<div id="department">
	                                    	<select class="form-control" name="department">
												<option value="">Chọn Bộ môn</option>
	                                    	</select>
                                    	</div>
                                    	<br>
                                    
	                                    <label >Thành viên</label>
	                                    <form:select path="JUSUPRJ_STAFFCODE" class="form-control" name="JUSUPRJ_STAFFCODE" id="staff">
	                                        <option value="">chọn</option>	                                    	                                    
	                                    	<c:forEach items="${staffList}" var="iStaff">
		                                        <option value="${iStaff.staff_Code}">${iStaff.staff_Name}</option>
	                                       	</c:forEach>
	                                    </form:select>
	                                    <form:errors path="JUSUPRJ_STAFFCODE" class="alert-danger"></form:errors>
	                                     
	                                    <%-- <div class="form-group">
		                                	<label for="projectMembers">Thành viên</label>
		                                	<div id="staff">
		                                	<select class="form-control" id="members">
		                                	<c:forEach items="${staffList}" var="aStaff">
		                                    	<option value="${aStaff.staff_Code}">${aStaff.staff_Name}</option>
		                                   	</c:forEach>
		                                	</select>
		                                	</div>
		                            	</div> --%>
		                             
	                                </div>
	                        
	                                <div class="form-group">
	                                    <label for="paperJConfName">Vai trò</label>
	                                    <form:select path="JUPSURJ_ROLECODE" class="form-control" name="JUPSURJ_ROLECODE">
	                                        <option value="">chọn</option>	                                    	        	                                    
	                                    	<c:forEach items="${juryRoleOfSubmittedProjecsList}" var="iRole">
		                                        <option value="${iRole.JUPRJROL_CODE}">${iRole.JUPRJROL_NAME}</option>
	                                       	</c:forEach>
	                                    </form:select>
	                                    <form:errors path="JUPSURJ_ROLECODE" class="alert-danger"></form:errors>
	                                </div>
	                                
									<!-- button type="button" id="filter" class="btn btn-primary filter">Lọc</button -->
	                                <button type="submit" class="btn btn-primary" id="addANewPaper">Lưu</button>
	                                <!-- <button type="reset" class="btn btn-primary">Clear</button> -->
	                                <button type="reset" class="btn btn-primary cancel">Hủy</button>
	                        </div>
	                        
	                    </div>
	                    <!-- /.row (nested) -->
                    </form:form>
                    
                    
                    <div class="dataTable_wrapper">
						<table class="table table-striped table-bordered table-hover"
							id="dataTables-example">
							<thead>
								<tr>
									<th title="Name of project call " > Đợt gọi đề tài </th>
									<th title="Name of staff ">Thành viên </th>
									<th title="Name of role ">Vai trò</th>
									<th>Edit</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${juryOfAnnouncedProjectCallList}" var="jury">
									<tr class="gradeX">
										<td><c:out value="${jury.JUSUPRJ_PRJCALLCODE}"/></td>
										<td><c:out value="${jury.JUSUPRJ_STAFFCODE}"/></td>
										<td><c:out value="${jury.JUPSURJ_ROLECODE}"/></td>
										<td class="center">
											<button type="button" id="removeJuryOfAnnouncedProjectCall" onclick="v_fRemoveJuryOfAnnouncedProjectCall(${jury.JUSUPRJ_ID});" class="btn btn-danger btn-xs" title="Remove">Xoá</button>
											<br>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<!-- /.table-responsive -->
                </div>
                <!-- /.panel-body -->
            </div>
            <!-- /.panel -->
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
</div>
<!-- /#page-wrapper -->

<!-- DataTables JavaScript -->
<script src="<c:url value="/assets/libs/datatables/media/js/jquery.dataTables.js"/>"></script>
<script src="<c:url value="/assets/libs/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.js"/>"></script>

<!-- Page-Level Demo Scripts - Tables - Use for reference -->


<script>
$(document).ready(function() {
    $('#dataTables-example').DataTable({
            responsive: false,
            "aoColumnDefs": [
                             { 'bSortable': false, 'aTargets': [3] }
                          ]
    });
    
    
    
    $('.add').click(function(){
    	window.location = baseUrl+"/cp/add-a-projectcall.html";
    });
});
</script>

<script>
function v_fRemoveJuryOfAnnouncedProjectCall(iJuryOfAnnouncedProjectCallId){
	var r = confirm("Bạn có thực sự muốn xoá không ?");
	if (r == true) {
		var sDeleteJuryOfAnnouncedProjectCallUrl = baseUrl + "/cp/delete-jury-of-announced-project-call/"+ iJuryOfAnnouncedProjectCallId +".html";
		window.location = sDeleteJuryOfAnnouncedProjectCallUrl;
	} else {
	    return false;
	}
}

function showDepartment(sFaculty)
{
	var sGeneratingUrl = "${baseUrl}/cpservice/getdepartments.html";
	var sFacultyCode = sFaculty.options[sFaculty.selectedIndex].value;  
	if (sFacultyCode.length > 0 ) { 
		 $.ajax({
				type: "POST",
				url: sGeneratingUrl,
				data: "sFacultyCode="+sFacultyCode,
				cache: false,
				beforeSend: function () { 
					//$('#department').html('<img src="loader.gif" alt="" width="24" height="24">');
				},
				success: function(html) {    
					console.log('html : ' + html);
					$("#department").html( html );
				}
			});
	}else{
		var sDepartment = '<select class="form-control" name="department">';
			sDepartment += '<option value="">Chọn Bộ môn</option>';
			sDepartment +=  '</select>';
		$("#department").html( sDepartment );
		
		var sStaffs = '<select class="form-control" name="staff">';
			sStaffs += '<option value="">Chọn Thành viên</option>';
			sStaffs +=  '</select>';
		$("#staff").html( sStaffs );
	}
}

function showStaff(sDepartment) {
	var sGeneratingUrl = "${baseUrl}/cpservice/getsinglestaffs.html";
	var sDepartmentCode = sDepartment.options[sDepartment.selectedIndex].value;
	if (sDepartmentCode.length > 0 ) { 
		 $.ajax({
				type: "POST",
				url: sGeneratingUrl,
				data: "sDepartmentCode="+sDepartmentCode,
				cache: false,
				beforeSend: function () { 
					//$('#staff').html('<img src="loader.gif" alt="" width="24" height="24">');
				},
				success: function(html) {    
					$("#staff").html( html );
				}
			});
	}
}

</script>

