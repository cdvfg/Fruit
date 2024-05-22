<template>
<div>
    <el-table :data="tableData" style="width: 100%">
        <el-table-column
            fixed
            prop="id"
            label="水果ID"
            width="160">
        </el-table-column>
        <el-table-column
            prop="name"
            label="水果名称"
            width="160">
        </el-table-column>
        <el-table-column
            prop="price"
            label="水果价格"
            width="160">
        </el-table-column>
        <el-table-column
            prop="stock"
            label="水果库存"
            width="160">
        </el-table-column>
        <el-table-column
            label="操作"
            width="100">
            <template slot-scope="scope">
                <el-button @click="fruitDelete(scope.row)" type="text" size="small">删除</el-button>
                <el-button @click="findFruit(scope.row)" type="text" size="small">编辑</el-button>
            </template>
        </el-table-column>
    </el-table>
</div>
</template>

<script>
import axios from 'axios';

    export default {
        methods: {
            findFruit(object){
                this.$router.push('/edit?id='+object.id)
            },
            fruitDelete(object) {

                let _this = this

                this.$confirm('是否确定要删除'+object.name+'？', '删除数据', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {

                    axios.delete('http://localhost:8181/fruit/delete/'+object.id).then(function (response) {
                        if(response.data){
                            _this.$alert(object.name+'删除成功！', '删除数据', {
                                confirmButtonText: '确定',
                                callback: action => {
                                    //跳转到/table
                                    location.reload()
                                }
                            });
                        }
                    })

                }).catch(() => {

                });

            }
        },
        created() {
            let _this = this
            axios.get('http://localhost:8181/fruit/list').then(function (response) {
                _this.tableData = response.data
            })
        },
        data() {
            return {
                tableData: null
            }
        }
    }
</script>