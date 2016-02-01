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
                                    <form:select path="projectCallCode" class="form-control" name="projectCallCode" disabled="${projectEdit.PROJ_Locked1 == 1 ? 'true' : ''}">
                                    	<c:forEach items="${projectCallsList}" var="projectCall">
	                                        <option value="${projectCall.PROJCALL_CODE}">${projectCall.PROJCALL_NAME}</option>
                                       	</c:forEach>
                                    </form:select>
                                    <form:errors path="projectCallCode" class="alert-danger"></form:errors>
                                </div>
                                <div class="panel panel-default">
			                        <div class="panel-heading">
			                            <label for="projectResult">Tên đề tài</label>
			                        </div>
			                        <div class="panel-body">
			                            <div class="tab-content">
			                                <div class="tab-pane fade in active" id="home">${projectEdit.PROJ_Name}</div>
			                            </div>
			                        </div>
			                    </div>

						        <div class="panel panel-default">
			                        <div class="panel-heading">
			                            <label for="projectResult">Nội dung</label>
			                        </div>
			                        <div class="panel-body">
			                            <div class="tab-content">
			                                <div class="tab-pane fade in active" id="home">${projectEdit.PROJ_Content}</div>
			                            </div>
			                        </div>
			                    </div>
			                    <div class="panel panel-default">
			                        <div class="panel-heading">
			                            <label for="projectResult">Kết quả đánh giá</label>
			                        </div>
			                        <div class="panel-body">
			                            <div class="tab-content">
			                                <div class="tab-pane fade in active" id="home">${projectEdit.PROJ_Result}</div>
			                            </div>
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
	                        <div class="col-lg-6">
                                <div class="panel panel-default">
			                        <div class="panel-heading">
			                            <label for="projectResult">Kinh phí (triệu VNĐ)</label>
			                        </div>
			                        <div class="panel-body">
			                            <div class="tab-content">
			                                <div class="tab-pane fade in active" id="home">${projectEdit.PROJ_TotalBudget}</div>
			                            </div>
			                        </div>
			                    </div>
						        <div class="panel panel-default">
			                        <div class="panel-heading">
			                            <label for="projectResult">Lý do thực hiện đề tài</label>
			                        </div>
			                        <div class="panel-body">
			                            <div class="tab-content">
			                                <div class="tab-pane fade in active" id="home">${projectEdit.PROJ_Motivation}</div>
			                            </div>
			                        </div>
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
</script>

