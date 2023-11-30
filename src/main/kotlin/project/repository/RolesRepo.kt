package project.repository

interface RolesRepo {
    fun storeRoles(userId: Long, roles: List<String>)
    fun getRoles(userId: Long): List<String>
    fun getRole(roleId: Long): String
    fun getRoleId(role: String): Long?
}