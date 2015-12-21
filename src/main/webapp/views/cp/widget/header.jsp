<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="home.html">Viện Công nghệ Thông tin và Truyền Thông - ĐHBK Hà Nội</a>
    </div>
    <!-- /.navbar-header -->

    <ul class="nav navbar-top-links navbar-right">
    		<!-- 
        	<li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <i class="fa fa-envelope fa-fw"></i>  <i class="fa fa-caret-down"></i>
            </a>
            
            <ul class="dropdown-menu dropdown-messages">
                <li>
                    <a href="#">
                        <div>
                            <strong>John Smith</strong>
                            <span class="pull-right text-muted">
                                <em>Yesterday</em>
                            </span>
                        </div>
                        <div>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eleifend...</div>
                    </a>
                </li>
                <li class="divider"></li>
                <li>
                    <a href="#">
                        <div>
                            <strong>John Smith</strong>
                            <span class="pull-right text-muted">
                                <em>Yesterday</em>
                            </span>
                        </div>
                        <div>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eleifend...</div>
                    </a>
                </li>
                <li class="divider"></li>
                <li>
                    <a href="#">
                        <div>
                            <strong>John Smith</strong>
                            <span class="pull-right text-muted">
                                <em>Yesterday</em>
                            </span>
                        </div>
                        <div>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eleifend...</div>
                    </a>
                </li>
                <li class="divider"></li>
                <li>
                    <a class="text-center" href="#">
                        <strong>Read All Messages</strong>
                        <i class="fa fa-angle-right"></i>
                    </a>
                </li>
            </ul>
        </li>
        <!-- /.dropdown-messages -->
        
        <!-- /.dropdown -->
        <!-- 
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <i class="fa fa-tasks fa-fw"></i>  <i class="fa fa-caret-down"></i>
            </a>
            <ul class="dropdown-menu dropdown-tasks">
                <li>
                    <a href="#">
                        <div>
                            <p>
                                <strong>Task 1</strong>
                                <span class="pull-right text-muted">40% Complete</span>
                            </p>
                            <div class="progress progress-striped active">
                                <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%">
                                    <span class="sr-only">40% Complete (success)</span>
                                </div>
                            </div>
                        </div>
                    </a>
                </li>
                <li class="divider"></li>
                <li>
                    <a href="#">
                        <div>
                            <p>
                                <strong>Task 2</strong>
                                <span class="pull-right text-muted">20% Complete</span>
                            </p>
                            <div class="progress progress-striped active">
                                <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" style="width: 20%">
                                    <span class="sr-only">20% Complete</span>
                                </div>
                            </div>
                        </div>
                    </a>
                </li>
                <li class="divider"></li>
                <li>
                    <a href="#">
                        <div>
                            <p>
                                <strong>Task 3</strong>
                                <span class="pull-right text-muted">60% Complete</span>
                            </p>
                            <div class="progress progress-striped active">
                                <div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%">
                                    <span class="sr-only">60% Complete (warning)</span>
                                </div>
                            </div>
                        </div>
                    </a>
                </li>
                <li class="divider"></li>
                <li>
                    <a href="#">
                        <div>
                            <p>
                                <strong>Task 4</strong>
                                <span class="pull-right text-muted">80% Complete</span>
                            </p>
                            <div class="progress progress-striped active">
                                <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width: 80%">
                                    <span class="sr-only">80% Complete (danger)</span>
                                </div>
                            </div>
                        </div>
                    </a>
                </li>
                <li class="divider"></li>
                <li>
                    <a class="text-center" href="#">
                        <strong>See All Tasks</strong>
                        <i class="fa fa-angle-right"></i>
                    </a>
                </li>
            </ul>
        </li>
        <!-- /.dropdown-tasks -->
        
        <!-- /.dropdown -->
        <!-- 
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <i class="fa fa-bell fa-fw"></i>  <i class="fa fa-caret-down"></i>
            </a>
            <ul class="dropdown-menu dropdown-alerts">
                <li>
                    <a href="#">
                        <div>
                            <i class="fa fa-comment fa-fw"></i> New Comment
                            <span class="pull-right text-muted small">4 minutes ago</span>
                        </div>
                    </a>
                </li>
                <li class="divider"></li>
                <li>
                    <a href="#">
                        <div>
                            <i class="fa fa-twitter fa-fw"></i> 3 New Followers
                            <span class="pull-right text-muted small">12 minutes ago</span>
                        </div>
                    </a>
                </li>
                <li class="divider"></li>
                <li>
                    <a href="#">
                        <div>
                            <i class="fa fa-envelope fa-fw"></i> Message Sent
                            <span class="pull-right text-muted small">4 minutes ago</span>
                        </div>
                    </a>
                </li>
                <li class="divider"></li>
                <li>
                    <a href="#">
                        <div>
                            <i class="fa fa-tasks fa-fw"></i> New Task
                            <span class="pull-right text-muted small">4 minutes ago</span>
                        </div>
                    </a>
                </li>
                <li class="divider"></li>
                <li>
                    <a href="#">
                        <div>
                            <i class="fa fa-upload fa-fw"></i> Server Rebooted
                            <span class="pull-right text-muted small">4 minutes ago</span>
                        </div>
                    </a>
                </li>
                <li class="divider"></li>
                <li>
                    <a class="text-center" href="#">
                        <strong>See All Alerts</strong>
                        <i class="fa fa-angle-right"></i>
                    </a>
                </li>
            </ul>
        </li>
        <!-- /.dropdown-alerts -->
        <!-- /.dropdown -->
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
            	${currentUserName}
                <i class="fa fa-user fa-fw"></i>  
                <i class="fa fa-caret-down"></i>
            </a>
            <ul class="dropdown-menu dropdown-user">
                <li><a href="<c:url value="${baseUrl}/cp/profile.html"/>"><i class="fa fa-user fa-fw"></i> Thông tin người dùng</a>
                </li>
                <li><a href="<c:url value="${baseUrl}/cp/changepass.html"/>"><i class="fa fa-gear fa-fw"></i> Cài đặt</a>
                </li>
                <li class="divider"></li>
                <li><a href="<c:url value="/j_spring_security_logout" />"><i class="fa fa-sign-out fa-fw"></i> Đăng xuất</a>
                </li>
            </ul>
            <!-- /.dropdown-user -->
        </li>
        <!-- /.dropdown -->
    </ul>
    <!-- /.navbar-top-links -->

    <div class="navbar-default sidebar" role="navigation">
        <div class="sidebar-nav navbar-collapse">
            <ul class="nav" id="side-menu">
            	<!-- 
                <li class="sidebar-search">
                    <div class="input-group custom-search-form">
                        <input type="text" class="form-control" placeholder="Search...">
                        <span class="input-group-btn">
                        <button class="btn btn-default" type="button">
                            <i class="fa fa-search"></i>
                        </button>
                    </span>
                    </div>
                </li>
                <!-- /input-group -->
                <c:if test="${currentUserRole eq 'ROLE_ADMIN' || currentUserRole eq 'SUPER_ADMIN'}">
	                <li>
	                    <a class="${users}" href="<c:url value="${baseUrl}/cp/users.html"/>"><i class="fa fa-user fa-fw"></i> Quản lý Users</a>
	                </li>

	                <%-- 
	                <li>
	                    <a class="${staffs}" href="#"><i class="fa fa-bar-chart-o fa-fw"></i> Quản lý Nhân sự</a>
	                </li>
	                 --%>
                </c:if>
                
                
                <li>
                    <a href="#"><i class="fa fa-tasks fa-fw"></i> Quản lý kê khai<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
		                <li>
		                    <a class="${papers}" href="<c:url value="${baseUrl}/cp/papers.html"/>"><i class="fa fa-book"></i> Quản lý Bài báo</a>
		                </li>
		                <li>
		                    <a class="${topics}" href="<c:url value="${baseUrl}/cp/topics.html"/>"><i class="fa fa-list-alt fa-fw"></i> Quản lý Đề tài</a>
		                </li>
		                <li>
		                    <a class="${patents}" href="<c:url value="${baseUrl}/cp/patents.html"/>"><i class="fa fa-file fa-fw"></i> Quản lý Bằng sáng chế</a>
		                </li>
		                <c:if test="${currentUserRole eq 'ROLE_ADMIN'}">
			                <li>
			                    <a class="${summary}" href="<c:url value="${baseUrl}/cp/summary.html"/>"><i class="fa fa-dashboard fa-fw"></i> Tổng hợp</a>
			                </li>
		                </c:if>
		           </ul>
		        </li>
		        
                <li>
                    <a href="#"><i class="fa fa-th-list fa-fw"></i> Quản lý thực hiện Đề tài<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
		                <li>
		                    <a href="<c:url value="${baseUrl}/cp/threads-listadd.html"/>"><i class="fa fa-briefcase"></i> Đăng ký Đề tài</a>
		                </li>
		                <li>
		                    <a href="<c:url value="${baseUrl}/cp/products.html"/>"><i class="fa fa-file-text"></i> Đăng ký Chuyên đề</a>
		                </li>
		                
		                <c:if test="${currentUserRole eq 'ROLE_ADMIN' || currentUserRole eq 'SUPER_ADMIN'}">
		                <li>
		                    <a href="<c:url value="${baseUrl}/cp/threads-approve.html"/>"><i class="fa fa-briefcase"></i> Phê duyệt đề tài</a>
		                </li>
		                
		                <li>
		                    <a href="<c:url value="${baseUrl}/cp/threads-evaluate.html"/>"><i class="fa fa-briefcase"></i> Nghiệm thu đề tài</a>
		                </li>
		                </c:if>
		                
		                <li>
		                    <a href="<c:url value="${baseUrl}/cp/threads.html"/>"><i class="fa fa-briefcase"></i> Thống kê đề tài</a>
		                </li>
		             </ul>
                </li>
                
                 
                <li>
                    <a href="#"><i class="fa fa-university fa-fw"></i> Quản lý danh mục<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a class="${falcuties}" href="#"><i class="fa fa-edit fa-fw"></i> Quản lý Khoa/Viện</a>
                        </li>
                        <li>
                            <a class="${departments}" href="#"><i class="fa fa-table fa-fw"></i> Quản lý Bộ môn</a>
                        </li>
                        <li>
	                    	<a class="${settings}" href="#"><i class="fa fa-wrench fa-fw"></i> Settings</a>
	                	</li>
                    </ul>
                    
                </li>
               
	                 
	                <%-- <li>
	                    <a class="${departments}" href="#"><i class="fa fa-table fa-fw"></i> Quản lý Bộ môn</a>
	                </li>
	                <li>
	                    <a class="${falcuties}" href="#"><i class="fa fa-edit fa-fw"></i> Quản lý Khoa/Viện</a>
	                </li>
	                <li>
	                    <a class="${settings}" href="#"><i class="fa fa-wrench fa-fw"></i> Settings</a>
	                </li> --%>
                
                <li>
                    <a class="${summary}" href="#"><i class="fa fa-wrench fa-fw"></i> Tổng hợp<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a class="${summary}" href="<c:url value="${baseUrl}/cp/generate-excel-01cn-02cn.html"/>"><i class="fa fa-dashboard fa-fw"></i> Mẫu 01CN-02CN</a>
                        </li>
                        <c:if test="${currentUserRole eq 'ROLE_ADMIN' || currentUserRole eq 'SUPER_ADMIN'}">
	                        <li>
	                            <a class="${summary}" href="<c:url value="${baseUrl}/cp/summary.html"/>"><i class="fa fa-dashboard fa-fw"></i> Mẫu 01BM-02BM-03BM</a>
	                        </li>
	                        <li>
	                            <a class="${summary}" href="<c:url value="${baseUrl}/cp/summary-kv01.html"/>"><i class="fa fa-dashboard fa-fw"></i> Mẫu 01 KV</a>
	                        </li>
	                        <li>
	                            <a class="${summary}" href="<c:url value="${baseUrl}/cp/summary-isi02.html"/>"><i class="fa fa-dashboard fa-fw"></i> Mẫu 02 ISI</a>
	                        </li>
	                        <li>
	                            <a class="${summary}" href="<c:url value="${baseUrl}/cp/summary-kv03.html"/>"><i class="fa fa-dashboard fa-fw"></i> Mẫu 03 KV</a>
	                        </li>
	                        
	                        <!--  
	                        <li>
	                            <a class="${summary}" href="<c:url value="${baseUrl}/cp/summary-kv-list-papers.html"/>"><i class="fa fa-dashboard fa-fw"></i> Danh sách tất cả bài báo</a>
	                        </li>
                        </c:if>
                    </ul>
                    <!-- /.nav-second-level -->
                </li>
            </ul>
            
        </div>
        <!-- /.sidebar-collapse -->
    </div>
    <!-- /.navbar-static-side -->
</nav>