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
            <h1 class="page-header">Tổng hợp comments phản biện đề tài</h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
        	<c:if test="${projectEdit.PROJ_Locked2 == 1}">
        	<form:form action="${baseUrl}/cp/edit-a-approveproject.html" method="POST" commandName="projectFormEdit" role="form" enctype="multipart/form-data">
	            <div class="panel panel-default">
	                <div class="panel-heading">
	                    Tổng hợp comments phản biện đề tài
	                </div>
	                <div class="panel-body">
	                    <div class="row">
	                        <div class="col-lg-6">
						       	<div class="form-group">
                                	<label for="projectComment1">Ý kiến phản biện 1</label>
	                                <textarea path="projectComment1"  name="projectComment1" id="projectComment1" class="form-control"></textarea>
	                                <form:errors path="projectComment1" class="alert-danger"></form:errors>
                            	</div>
			                    <div class="form-group">
                                   <label>Chọn trạng thái phê duyệt</label>
                                   <form:select path="projectStatusCode" class="form-control" name="projectStatusCode">
                                        <option value="ACCEPT_REVISION">Phê duyệt nhưng cần chỉnh sửa</option>
                                        <option value="APPROVED">Phê duyệt</option>
                                        <option value="REJECT">Từ chối</option>
                                   </form:select>
                                   <form:errors path="projectStatusCode" class="alert-danger"></form:errors>
                                </div>
			                    <!-- Buttons -->
                               	 <c:if test="${projectEdit.PROJ_Locked2 == 1}">
	                                 <button type="submit" class="btn btn-primary">Lưu</button>
	                                 <input type="hidden" value="${projectEdit.PROJ_ID}" name="projectId" id="projectId" />
	                                 <input type="hidden" value="${projectEdit.PROJ_Name}" name="projectName" id="projectName" />
                                 </c:if>
                                 <button type="reset" class="btn btn-info cancel">Hủy</button>
                                 <c:if test="${projectEdit.PROJ_Locked2 != 1}">
                                 	<button type="reset" class="btn btn-danger" onclick="v_fConfirmProject(${projectEdit.PROJ_ID})">Xác nhận</button>
                                 </c:if>
	                        </div>
	                        <div class="col-lg-6">
	                            <div class="form-group">
                                	<label for="projectComment2">Ý kiến phản biện 2</label>
	                                <textarea path="projectComment2"  name="projectComment2" id="projectComment2" class="form-control"></textarea>
	                                <form:errors path="projectComment2" class="alert-danger"></form:errors>
                            	</div>
	                        </div>
	                        <!-- /.col-lg-6 (nested) -->
	                    </div>
	                    <!-- /.row (nested) -->
	                </div>
	                <!-- /.panel-body -->
	            </div>
	            </form:form>
	            <!-- /.panel -->
            </c:if>
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
		window.location = baseUrl+"/cp/collect-comments.html";
	});
});

function v_fConfirmProject(iProjectId){
	if(confirm("Chú ý ! Đề tài sau khi phê duyệt sẽ không thể chỉnh sửa được.")){
		var sSubmitProjectUrl = baseUrl + "/cp/confirmproject/"+iProjectId+".html";
		window.location = sSubmitProjectUrl;
    }
    else{
        return false;
    }
}
</script>

