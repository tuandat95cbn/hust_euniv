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
                    <form:form action="${baseUrl}/cp/edit-a-project.html" method="POST" commandName="projectFormEdit" role="form" enctype="multipart/form-data">
	                    <div class="row">
	                        <div class="col-lg-6">
	                       		<div class="form-group">
                                    <label>Chọn đợt đề tài*</label>
                                    <form:select path="projectCallCode" class="form-control" name="projectCallCode">
                                    	<c:forEach items="${projectCallsList}" var="projectCall">
	                                        <option value="${projectCall.PROJCALL_CODE}">${projectCall.PROJCALL_NAME}</option>
                                       	</c:forEach>
                                    </form:select>
                                    <form:errors path="projectCallCode" class="alert-danger"></form:errors>
                                </div>
                                <div class="form-group">
                                    <label for="projectName">Tên đề tài*</label>
                                    <form:input path="projectName" class="form-control" name="projectName" value="${projectEdit.PROJ_Name}" placeholder="Project Name"></form:input>
    								<form:errors path="projectName" class="alert-danger"></form:errors>
                                </div>
                               <div class="form-group">
                                	<label for="projectResult">Nội dung</label>
	                                <textarea path="projectContent"  name="projectContent" id="projectContent" class="form-control">${projectEdit.PROJ_Content}</textarea>
	                                <form:errors path="projectContent" class="alert-danger"></form:errors>
                            	</div>
                               	
                                <div class="form-group">
                                    <label for="projectResult">Kết quả đánh giá</label>
                                    <textarea path="projectResult" name="projectResult" id="projectResult" class="form-control">${projectEdit.PROJ_Result}</textarea>
   									<form:errors path="projectResult" class="alert-danger"></form:errors>
                               	</div>
                                 <button type="submit" class="btn btn-primary">Lưu</button>
                                 <input type="hidden" value="${projectEdit.PROJ_ID}" name="projectId" id="projectId" />
                                 <button type="reset" class="btn btn-primary cancel">Hủy</button>
	                        </div>
	                        <div class="col-lg-6">
                               	<div class="form-group">
                                    <label for="projectBudget">Kinh phí (triệu VNĐ)</label>
                                    <form:input path="projectBudget" class="form-control" name="projectBudget" value="${projectEdit.PROJ_TotalBudget}" placeholder="Budget"></form:input>
    								<form:errors path="projectBudget" class="alert-danger"></form:errors>
                                </div>
                                
                                <div class="form-group">
                                    <label for="projectMotivation">Lý do thực hiện đề tài</label>
                                    <textarea path="projectMotivation" name="projectMotivation" id="projectMotivation" class="form-control">${projectEdit.PROJ_Motivation}</textarea>
    								<form:errors path="projectMotivation" class="alert-danger"></form:errors>
                                </div>
	                        </div>
	                        <!-- /.col-lg-6 (nested) -->
	                    </div>
	                    <!-- /.row (nested) -->
                    </form:form>
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

<script type="text/javascript">

$('textarea').each( function() {
    CKEDITOR.replace( $(this).attr('id') );
});
 
$(document).ready(function(){
	$('button.cancel').click(function(){
		window.location = baseUrl+"/cp/list-projects.html";
	});
});

</script>
