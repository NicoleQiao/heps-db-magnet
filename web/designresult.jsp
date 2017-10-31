<%-- 
    Document   : querydesignjsp
    Created on : 2017-10-24, 15:40:32
    Author     : qiaoys
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="jquery-easyui-1.5.3/themes/default/easyui.css">
        <link rel="stylesheet" type="text/css" href="jquery-easyui-1.5.3/themes/icon.css">     
        <script type="text/javascript" src="jquery-easyui-1.5.3/jquery.min.js"></script>
        <script type="text/javascript" src="jquery-easyui-1.5.3/jquery.easyui.min.js"></script>

        <title>查询-磁铁设计</title>
        <style type="text/css">
            span{
                font-size: 14px;
                font-weight: bold;
            }
            .a-upload {
                padding: 4px 10px;
                width: 350px;
                line-height: 20px;
                position: relative;
                cursor: pointer;
                color: #444;
                background: #fafafa;
                background-repeat: repeat-x;    
                border: 1px solid #bbb;
                border-radius: 5px 5px 5px 5px;
                overflow: hidden;
                display: inline-block;
                *display: inline;
                *zoom: 1
            }
            .a-upload  input {
                position: absolute;
                font-size: 100px;
                right: 0;
                top: 0;
                opacity: 0;
                filter: alpha(opacity=0);
                cursor: pointer
            }
            .a-upload:hover {
                color:  #000000;
                background: #eaf2ff;
                border: 1px solid #b7d2ff;
                text-decoration: none
            } 
        </style>
    </head>
    <body>
        <h2>查询磁铁设计</h2>  
        <div class="easyui-panel" style="height:820px;padding:10px 60px;position: relative;" >
            <div style="position:absolute;left:0;right:0;width: 1300px;margin:0 auto;font-size:14px;">
                <form action="QueryDesign" method="post" >
                    <div style="width: 1200px;height: 30px">
                        <div id="info1" style="position:absolute;width: 200px">
                            <span>磁铁种类：</span> 
                            <select  id="magtype" name="magtype" style="width: 100px; height: 25px" >
                                <option value="">未选择</option>
                                <option value="二极铁">二极铁</option>
                                <option value="四极铁">四极铁</option>
                                <option value="六极铁">六极铁</option>
                                <option value="八极铁">八极铁</option>
                            </select> 
                        </div>
                        <div id="info2" style="position:absolute;width: 200px;left:200px">
                            <span>磁铁型号：</span>
                            <select  id="magfamily" name="magfamily" style="width: 100px;height: 25px" >
                                <option value="">未选择</option>
                                <option value="1">I</option>
                                <option value="2">II</option>
                            </select>
                        </div>
                        <div style="margin:10px 0;"></div>
                        <div id="length" style="position:absolute;top: 50px" >                     
                            <span>有效长度范围：</span>                                
                            <input type="text"  size= 8 autocomplete="off" value="">
                            <span> - </span>                         
                            <input type="text" size= 8 autocomplete="off" value="">
                        </div>                  
                    </div>
                    <div id="intensity" style="position:absolute;top: 90px">
                        <span>磁场强度范围：</span>
                        <input name="sel_b" type="checkbox" value="" /><label>二极分量 </label>     
                        <input type="text" size=8 autocomplete="off" value="">
                        <span> - </span>
                        <input type="text" size= 8 autocomplete="off" value="">
                        <input name="sel_q" type="checkbox" value="" /><label>四极分量 </label>     
                        <input type="text" size=8 autocomplete="off" value="">
                        <span> - </span>
                        <input type="text" size= 8 autocomplete="off" value="">
                        <input name="sel_s" type="checkbox" value="" /><label>六极分量 </label>     
                        <input type="text" size=8 autocomplete="off" value="">
                        <span> - </span>
                        <input type="text" size= 8 autocomplete="off" value="">
                        <input name="sel_o" type="checkbox" value="" /><label>八极分量 </label>     
                        <input type="text" size=8 autocomplete="off" value="">
                        <span> - </span>
                        <input type="text" size= 8 autocomplete="off" value="">
                    </div>
                    <div style="position:absolute;top:130px;bottom: 0; left:0;right:0;text-align: center">                    
                        <input style="width:90px; font-size: 14px" class="a-upload" type="submit" value="查询" >
                    </div>                   
                </form>                
                <div style="position: absolute;top: 170px;width: 1200px">
                    <table id="dg" name="dg" class="easyui-datagrid" title="Frozen Rows in DataGrid" 
                           data-options="
                           singleSelect: true,
                           collapsible: true,                           
                           rownumbers: true,
                           dataType:'json',
                           remoteSort:true,
                           toolbar:toolbar,
                           url: 'DesignResult',
                           method: 'get',
                           onLoadSuccess: function(){    

                           }
                           ">
                        <thead data-options="frozen:true">
                            <tr>
                                <th data-options="field:'designid',width:80,sortable:true">ID</th>
                                <th data-options="field:'magtype',width:80">磁铁类型</th>
                                <th data-options="field:'magfamily',width:80">磁铁型号</th>
                            </tr>
                        </thead>
                        <thead>
                            <tr>                                
                                <th data-options="field:'designedby',width:90,formatter:formatPrice">设计人</th>
                                <th data-options="field:'approvedby',width:90,formatter:formatPrice">负责人</th>
                                <th data-options="field:'remark',width:90,formatter:formatPrice">备注</th>
                                <th data-options="field:'length',width:90,formatter:formatPrice">有效长度</th>
                                <th data-options="field:'aperture',width:90,formatter:formatPrice">磁铁孔径</th>
                                <th data-options="field:'min_gap',width:120,formatter:formatPrice">相邻磁极最小间隙</th>
                                <th data-options="field:'useful_field',width:90,formatter:formatPrice">好场区范围</th>
                                <th data-options="field:'intensityB',width:90,formatter:formatPrice">二极分量</th>
                                <th data-options="field:'intensityQ',width:90,formatter:formatPrice">四极分量</th>
                                <th data-options="field:'intensityS',width:90,formatter:formatPrice">六极分量</th>
                                <th data-options="field:'intensityO',width:90,formatter:formatPrice">八极分量</th>
                                <th data-options="field:'sys',width:90,formatter:formatPrice">系统分量</th>
                                <th data-options="field:'non_sys',width:90,formatter:formatPrice">非系统分量</th>
                            </tr>
                        </thead>
                    </table>
                </div>

            </div>            
        </div>
        <script type="text/javascript">
            function formatPrice(val, row) {
                if (val === 'null') {
                    return '';
                } else {
                    return val;
                }
            }
            var toolbar = [{
                    text: 'Add',
                    iconCls: 'icon-add',
                    handler: function () {
                        var row = $('#dg').datagrid('getSelected');
                        if (row) {
                            alert('Item ID:' + row.designid + "Price:" + row.length);
                        }
                        //alert('add');

                    }
                }, {
                    text: 'Cut',
                    iconCls: 'icon-cut',
                    handler: function () {
                        alert('cut');
                    }
                }, '-', {
                    text: 'Save',
                    iconCls: 'icon-save',
                    handler: function () {
                        alert('save');
                    }
                }];
        </script>
    </body>
</html>