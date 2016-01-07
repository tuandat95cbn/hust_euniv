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
            <h1 class="page-header">Thêm mới đề tài</h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Thêm mới đề tài
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
                    <form:form action="${baseUrl}/cp/save-a-thread.html" method="POST" commandName="projectFormEdit" role="form" enctype="multipart/form-data">
	                    <div class="row">
	                        <div class="col-lg-6">
                                <div class="form-group">
                                    <label for="projectName">Tên đề tài*</label>
                                    <form:input path="projectName" class="form-control" name="projectName" placeholder="Project Name"></form:input>
    								<form:errors path="projectName" class="alert-danger"></form:errors>
                                </div>
                               <div class="form-group">
                                	<label for="projectResult">Nội dung</label>
	                                <form:textarea path="projectContent"  name="projectContent" class="form-control"/>
	                                <form:errors path="projectContent" class="alert-danger"></form:errors>
                            	</div>
                               	
                                <div class="form-group">
                                    <label for="projectResult">Kết quả đánh giá</label>
                                    <form:textarea path="projectResult" name="projectResult" value="" class="form-control" />
   									<form:errors path="projectResult" class="alert-danger"></form:errors>
                               	</div>
                                 <button type="submit" class="btn btn-primary">Lưu</button>
                                 <button type="reset" class="btn btn-primary cancel">Hủy</button>
	                        </div>
	                        <div class="col-lg-6">
                               	<div class="form-group">
                                    <label for="projectBudget">Kinh phí (triệu VNĐ)</label>
                                    <form:input path="projectBudget" class="form-control" name="projectBudget" placeholder="Budget"></form:input>
    								<form:errors path="projectBudget" class="alert-danger"></form:errors>
                                </div>
                                
                                <div class="form-group">
                                    <label for="projectMotivation">Lý do thực hiện đề tài</label>
                                    <form:textarea path="projectMotivation" name="projectMotivation" class="form-control"/>
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

