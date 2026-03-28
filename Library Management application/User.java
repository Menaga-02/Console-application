class User{
    int id;
    String name;
    String email;
    String password;
    String role;
    double deposit=1500;
    double fine=0;
User(int id,String name,String email,String password,String role){
    this.id=id;
    this.name=name;
    this.email=email;
    this.password=password;
    this.role=role;
}
}
