<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<!-- DataTables CSS -->
<link href="<c:url value="/assets/libs/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css"/>" rel="stylesheet">

<!-- DataTables Responsive CSS -->
<link href="<c:url value="/assets/libs/datatables-responsive/css/dataTables.responsive.css" />" rel="stylesheet">

<!-- Jquery UI CSS -->
<link href="<c:url value="/assets/css/jquery-ui.css" />" rel="stylesheet">

<!-- jQuery UI -->
<script src="<c:url value="/assets/js/jquery-ui.js"/>"></script>
<script src="<c:url value="/assets/libs/ckeditor/ckeditor.js"/>"></script>	   
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">Chỉnh sửa đề tài</h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
        <form:form action="${baseUrl}/cp/edit-a-project.html" method="POST" commandName="projectFormEdit" role="form" enctype="multipart/form-data">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Chỉnh sửa đề tài
                </div>
                <div class="panel-body">
                	<c:if test="${status != null}">
	                	<div class="alert alert-success">
	                        ${status}. <a href="<c:url value="${baseUrl}/cp/list-projects.html"/>" class="alert-link">Quay lại</a>.
	                    </div>
                    </c:if>
                    <c:if test="${err != null}">
	                	<div class="alert alert-warning">${err}</div>
                    </c:if>
                    <div class="row">
                        <div class="col-lg-6">
                       		<div class="form-group">
                                   <label>Chọn đợt đề tài*</label>
                                   <form:select path="projectCallCode" class="form-control" name="projectCallCode" disabled="${projectEdit.PROJ_Locked1 == 1 ? 'true' : ''}">
                                   	<c:forEach items="${projectCallsList}" var="projectCall">
                                        <option value="${projectCall.PROJCALL_CODE}">${projectCall.PROJCALL_NAME}</option>
                                      	</c:forEach>
                                   </form:select>
                                   <form:errors path="projectCallCode" class="alert-danger"></form:errors>
                               </div>
                               <div class="form-group">
                                   <label for="projectName">Tên đề tài*</label>
                                   <form:input path="projectName" class="form-control" name="projectName" disabled="${projectEdit.PROJ_Locked1 == 1 ? 'true' : ''}" value="${projectEdit.PROJ_Name}" placeholder="Project Name"></form:input>
   								<form:errors path="projectName" class="alert-danger"></form:errors>
                               </div>
                              <div class="form-group">
                                   <label for="projectStartDate">Thời gian bắt đầu</label>
                                   <form:input path="projectStartDate" class="form-control" name="projectStartDate" disabled="${projectEdit.PROJ_Locked1 == 1 ? 'true' : ''}" value="${projectEdit.PROJ_StartDate}" placeholder="Project Start Date"></form:input>
   								<form:errors path="projectStartDate" class="alert-danger"></form:errors>
                               </div>
                               <div class="form-group">
                                   <label for="projectEndDate">Thời gian hoàn thành</label>
                                   <form:input path="projectEndDate" class="form-control" name="projectEndDate" disabled="${projectEdit.PROJ_Locked1 == 1 ? 'true' : ''}" value="${projectEdit.PROJ_EndDate}" placeholder="Project End Date"></form:input>
   								<form:errors path="projectEndDate" class="alert-danger"></form:errors>
                               </div>
                               <div class="form-group">
                                   <label for="projectBudget">Kinh phí (triệu VNĐ)</label>
                                   <form:input path="projectBudget" class="form-control" name="projectBudget" disabled="${projectEdit.PROJ_Locked1 == 1 ? 'true' : ''}" value="${projectEdit.PROJ_TotalBudget}" placeholder="Budget"></form:input>
   								<form:errors path="projectBudget" class="alert-danger"></form:errors>
                               </div>
                               <div class="form-group">
                                   <label>Đơn vị*</label>
                                   <form:select path="falcutyAddress" class="form-control" name="falcutyAddress">
                                   	<c:forEach items="${listFaculty}" var="faculty">
                                       	<option value="${faculty.faculty_Code}" <c:if test="${faculty.faculty_Code == projectEdit.PROJ_FacultyCode}">selected</c:if> >${faculty.faculty_Name}</option>
                                     		</c:forEach>
                                   </form:select>
                                   <form:errors path="falcutyAddress" class="alert-danger"></form:errors>
                               </div>
                               <c:choose>
								<c:when test="${projectEdit.PROJ_Locked1 != 1}">
	                               	<div class="form-group">
	                                	<label for="projectResult">Nội dung</label>
		                                <textarea path="projectContent"  name="projectContent" id="projectContent" class="form-control textarea">${projectEdit.PROJ_Content}</textarea>
		                                <form:errors path="projectContent" class="alert-danger"></form:errors>
	                            	</div>
		                            	<div class="form-group">
	                                    <label for="projectResult">Sản phẩm chuyển giao kết quả nghiên cứu và đia chỉ ứng dụng</label>
	                                    <textarea path="projectResult"  name="projectResult" id="projectResult" class="form-control textarea">${projectEdit.PROJ_Result}</textarea>
	   									<form:errors path="projectResult" class="alert-danger"></form:errors>
	                               	</div>
	                               	<div class="form-group">
	                                    <label for="projectOtherFees">Kinh phí vật tư, vật liệu,…</label>
	                                    <form:input path="projectOtherFees" class="form-control" name="projectOtherFees" placeholder="Other Fees"></form:input>
	   									<form:errors path="projectOtherFees" class="alert-danger"></form:errors>
	                               	</div>
							    </c:when>    
							    <c:otherwise>
							        <div class="panel panel-default">
				                        <div class="panel-heading">
				                            <label for="projectResult">Nội dung</label>
				                        </div>
				                        <div class="panel-body">
				                            <div class="tab-content">
				                                <div class="tab-pane fade in active">${projectEdit.PROJ_Content}</div>
				                            </div>
				                        </div>
				                    </div>
				                    <div class="panel panel-default">
				                        <div class="panel-heading">
				                            <label for="projectResult">Sản phẩm chuyển giao kết quả nghiên cứu và đia chỉ ứng dụng</label>
				                        </div>
				                        <div class="panel-body">
				                            <div class="tab-content">
				                                <div class="tab-pane fade in active">${projectEdit.PROJ_Result}</div>
				                            </div>
				                        </div>
				                    </div>
				                    <div class="panel panel-default">
				                        <div class="panel-heading">
				                            <label for="projectResult">Kinh phí vật tư, vật liệu,…</label>
				                        </div>
				                        <div class="panel-body">
				                            <div class="tab-content">
				                                <div class="tab-pane fade in active"></div>
				                            </div>
				                        </div>
				                    </div>
							    </c:otherwise>
							</c:choose>
                        </div>
                        <div class="col-lg-6">
                              	<div class="form-group">
                                   <label for="projectLeader">Chủ nhiệm đề tài</label>
                                   <input path="projectLeader" class="form-control" disabled name="projectLeader" value="${currentUserName}" />
                               </div>
                               <c:choose>
								<c:when test="${projectEdit.PROJ_Locked1 != 1}">
									<div class="form-group">
	                                    <label for="projectSurvey">Tổng quan tình hình thực hiện đề tài</label>
	                                    <textarea path="projectSurvey" name="projectSurvey" id="projectSurvey" class="form-control textarea">${projectEdit.PROJ_Survey}</textarea>
	   									<form:errors path="projectSurvey" class="alert-danger"></form:errors>
	                               	</div>
	                                <div class="form-group">
	                                    <label for="projectMotivation">Tính cấp thiết đề tài</label>
	                                    <textarea path="projectMotivation" name="projectMotivation" id="projectMotivation" class="form-control textarea">${projectEdit.PROJ_Motivation}</textarea>
	    								<form:errors path="projectMotivation" class="alert-danger"></form:errors>
	                                </div>
	                                <div class="form-group">
	                                    <label for="projectObjective">Mục tiêu</label>
	                                    <textarea path="projectObjective" name="projectObjective" id="projectObjective" class="form-control textarea">${projectEdit.PROJ_Objective}</textarea>
	   									<form:errors path="projectObjective" class="alert-danger"></form:errors>
	                               	</div>
							    </c:when>    
							    <c:otherwise>
							    	<div class="panel panel-default">
				                        <div class="panel-heading">
				                            <label for="projectResult">Tổng quan tình hình thực hiện đề tài</label>
				                        </div>
				                        <div class="panel-body">
				                            <div class="tab-content">
				                                <div class="tab-pane fade in active" id="home">${projectEdit.PROJ_Survey}</div>
				                            </div>
				                        </div>
				                    </div>
							        <div class="panel panel-default">
				                        <div class="panel-heading">
				                            <label for="projectResult">Tính cấp thiết đề tài</label>
				                        </div>
				                        <div class="panel-body">
				                            <div class="tab-content">
				                                <div class="tab-pane fade in active" id="home">${projectEdit.PROJ_Motivation}</div>
				                            </div>
				                        </div>
				                    </div>
				                    <div class="panel panel-default">
				                        <div class="panel-heading">
				                            <label for="projectResult">Mục tiêu</label>
				                        </div>
				                        <div class="panel-body">
				                            <div class="tab-content">
				                                <div class="tab-pane fade in active" id="home">${projectEdit.PROJ_Objective}</div>
				                            </div>
				                        </div>
				                    </div>
							    </c:otherwise>
							</c:choose>
                        </div>
                        <!-- /.col-lg-6 (nested) -->
                    </div>
                    <!-- /.row (nested) -->
                </div>
                <!-- /.panel-body -->
                
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-6">
                        	<div class="form-group">
                                <label for="projectMembers">Thành viên</label>
                                <select class="form-control" id="projectMembers">
                                	<c:forEach items="${staffList}" var="aStaff">
                                    	<option value="${aStaff.staff_Code}">${aStaff.staff_Name}</option>
                                   	</c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                               	<label for="taskContent">Nội dung công việc</label>
                                <textarea id="taskContent" class="form-control"></textarea>
                           	 </div>
                             <button type="button" class="btn btn-primary btn-xs" onclick="v_fAddMember();">Thêm</button>
                        </div>
                        <div class="col-lg-6">
                        	<div class="form-group">
                                <label for="memberRole">Vai trò</label>
                                <select class="form-control" id="memberRole">
                                	<c:forEach items="${memberRolesList}" var="memberRole">
                                     <option value="${memberRole.PROJPARTIROLE_Code}">${memberRole.PROJPARTIROLE_Description}</option>
                                   	</c:forEach>
                                </select>
                            </div>
                           	 <div class="form-group">
                                <label for="memberWorkingDays">Số ngày công</label>
                                <input class="form-control" id="memberWorkingDays" placeholder="Working days" />
                             </div>
                             <div class="form-group">
                                <label for="taskBudget">Thành tiền</label>
                                <input class="form-control" id="taskBudget" placeholder="Fee" />
                             </div>
                        </div>
                        <!-- /.col-lg-6 (nested) -->
                    </div>
                    <!-- /.row (nested) -->
                    <div class="panel-body">
                    	<div class="panel panel-default">
	                        <div class="panel-heading">
	                            Thành viên
	                        </div>
	                        <!-- /.panel-heading -->
	                        <div class="panel-body">
	                            <div class="table-responsive">
	                                <table class="table table-striped table-bordered table-hover" id="projectMemberList">
	                                    <thead>
	                                        <tr>
	                                            <th>Tên</th>
	                                            <th>Vai trò</th>
	                                            <th>Nội dung</th>
	                                            <th>Số ngày công</th>
	                                            <th>Thành tiền</th>
	                                            <th>Hủy</th>
	                                        </tr>
	                                    </thead>
	                                    <tbody>
		                                    <c:choose>
												<c:when test="${projectTasks != null}">
			                                    	<c:forEach items="${projectTasks}" var="task">
			                                    		<tr>
				                                            <td>
				                                            	<c:out value="${task.staffProject.staff_Name}"/>
				                                            	<input name='projectMembers' type='hidden' value="${task.PRJTSK_StaffCode}" />
				                                            </td>
				                                            <td>
				                                            	<c:out value="${task.participationRoles.PROJPARTIROLE_Description}"/>
				                                            	<input name='projectMemberRole' type='hidden' value="${task.PRJTSK_RoleCode}" />
				                                            </td>
				                                            <td>
				                                            	<c:out value="${task.PRJTSK_Task}"/>
				                                            	<input name='projectMemberTasks' type='hidden' value="${task.PRJTSK_Task}" />
				                                            </td>
				                                            <td>
				                                            	<c:out value="${task.PRJTSK_NRBDay}"/>
				                                            	<input name='projectMemberWorkingDays' type='hidden' value="${task.PRJTSK_NRBDay}" />
				                                            </td>
				                                            <td>
				                                            	<c:out value="${task.PRJTSK_Cost}"/>
				                                            	<input name='projectMemberBudget' type='hidden' value="${task.PRJTSK_Cost}" />
				                                            </td>
				                                            <td><button type='button' onclick='v_fClearMember(this);' class='btn btn-warning btn-xs' title='Hủy' >Clear</button></td>
				                                        </tr>
			                                    	</c:forEach>
		                                    	</c:when>    
									    		<c:otherwise>
			                                        <tr class="no-records-found">
			                                        	<td colspan="6" align="center">Chưa có thành viên</td>
			                                        </tr>
			                                    </c:otherwise>
		                                    </c:choose>
	                                    </tbody>
	                                </table>
	                            </div>
	                            <!-- /.table-responsive -->
	                        </div>
	                        <!-- /.panel-body -->
	                    </div>
                    </div>
                   	<!-- Buttons -->
                  	<c:if test="${projectEdit.PROJ_Locked1 != 1}">
                    	<button type="submit" class="btn btn-primary">Lưu</button>
                    </c:if>
                    <input type="hidden" value="${projectEdit.PROJ_ID}" name="projectId" id="projectId" />
                    <button type="reset" class="btn btn-info cancel">Hủy</button>
                    <c:if test="${projectEdit.PROJ_Locked1 == 1}">
                    	<button type="reset" class="btn btn-success" onclick="v_fGeneratePDF(${projectEdit.PROJ_ID})">Xuất PDF</button>
                    </c:if>
                    <c:if test="${projectEdit.PROJ_Locked1 != 1}">
                    	<button type="reset" class="btn btn-danger" onclick="v_fSendProject(${projectEdit.PROJ_ID})">Gửi đề tài</button>
                    </c:if>
                </div>
                <!-- /.panel-body -->
            </div>
            <!-- /.panel -->
            </form:form>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
</div>
<!-- /#page-wrapper -->

<script type="text/javascript">

$('.textarea').each( function() {
    CKEDITOR.replace( $(this).attr('id') );
});
 
$(document).ready(function(){
	$('button.cancel').click(function(){
		window.location = baseUrl+"/cp/list-projects.html";
	});
});

function v_fGeneratePDF(iProjectId){
	var sGeneratePdfUrl = baseUrl + "/cp/generatepdf/"+iProjectId+".html";
	window.location = sGeneratePdfUrl;
}

function v_fSendProject(iProjectId){
	if(confirm("Chú ý ! Đề tài sau khi gửi sẽ không thể chỉnh sửa được.")){
		var sSubmitProjectUrl = baseUrl + "/cp/sendproject/"+iProjectId+".html";
		window.location = sSubmitProjectUrl;
    }
    else{
        return false;
    }
}

function v_fAddMember(){
	var sMemberCode = $("select#projectMembers").find(":selected").val();
	var sMemberName = $("select#projectMembers").find(":selected").text();
	var sMemberRoleCode = $("#memberRole").find(":selected").val();
	var sMemberRoleDescription = $("#memberRole").find(":selected").text();
	var sTask = $("#taskContent").val();
	var iMemberWorkingDays = $("#memberWorkingDays").val();
	var iBudget = $("#taskBudget").val();
	
	var sAddedMember = "";
	if(sMemberName != "" && sMemberCode != "")
	{
		// Remove no records found column
		$("table#projectMemberList tbody tr.no-records-found").remove();
		$("#taskContent").val("");
		$("#memberWorkingDays").val("");
		$("#taskBudget").val("");
		
		sAddedMember 	+= "<tr>";
		sAddedMember 	+= "<td><span>"+sMemberName+"</span><input name='projectMembers' type='hidden' value='"+sMemberCode+"'/></td>";
		sAddedMember 	+= "<td><span>"+sMemberRoleDescription+"</span><input name='projectMemberRole' type='hidden' value='"+sMemberRoleCode+"'/></td>";
		sAddedMember 	+= "<td><span>"+sTask+"</span><input name='projectMemberTasks' type='hidden' value='"+sTask+"'/></td>";
		sAddedMember 	+= "<td><span>"+iMemberWorkingDays+"</span><input name='projectMemberWorkingDays' type='hidden' value='"+iMemberWorkingDays+"'/></td>";
		sAddedMember 	+= "<td><span>"+iBudget+"</span><input name='projectMemberBudget' type='hidden' value='"+iBudget+"'/></td>";
		sAddedMember 	+= "<td><button type='button' onclick='v_fClearMember(this);' class='btn btn-warning btn-xs' title='Hủy' >Clear</button></td>";
		sAddedMember 	+= "</tr>";
		$("table#projectMemberList tbody").append(sAddedMember);
	}
}

function v_fClearMember(the_oElement){
	$(the_oElement).parents("tr").remove();
}
</script>

