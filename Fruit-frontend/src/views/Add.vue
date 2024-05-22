<template>
    <el-form ref="fruitRules" :model="fruit" :rules="rules" label-width="80px" class="demo-ruleForm" style="width: 600px">
        <el-form-item label="名称" prop="name">
            <el-input v-model="fruit.name"></el-input>
        </el-form-item>
        <el-form-item label="价格" prop="price">
            <el-input v-model.number="fruit.price"></el-input>
        </el-form-item>
        <el-form-item label="储存量" prop="stock">
            <el-input v-model.number="fruit.stock"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit('fruitRules')">立即创建</el-button>
            <el-button @click="$router.back()">取消</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
import axios from 'axios';

    export default {
        name: "Add",
        data() {
            return {
                fruit: {
                    name: '',
                    price: '',
                    stock: '',
                },
                rules:{
                    name:[
                        { required: true, message: '请输入水果名称', trigger: 'blur' }
                    ],
                    price:[
                        { required: true, message: '请输入价格', trigger: 'blur' },
                        { type: 'number', message: '价格必须为数字值'}
                    ],
                    stock:[
                        { required: true, message: '请输入储存量', trigger: 'blur' },
                        { type: 'number', message: '储存量必须为数字值'}
                    ],
                }
            }
        },
        methods: {
            onSubmit(formName){
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        let _this = this
                        axios.post('http://localhost:8181/fruit/add',this.fruit).then(function (response) {
                            if(response.data){
                                _this.$alert(_this.fruit.name+'添加成功！', '添加数据', {
                                    confirmButtonText: '确定',
                                    callback: action => {
                                        //跳转到/table
                                        _this.$router.push('/table')
                                    }
                                });
                            }
                        })
                    }
                });
            }
        }
    }
</script>

<style scoped>

</style>