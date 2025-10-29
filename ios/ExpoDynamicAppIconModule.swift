import ExpoModulesCore

public class ExpoDynamicAppIconModule: Module {
    public func definition() -> ModuleDefinition {

        Name("ExpoDynamicAppIcon")

        Function("setAppIconIOS") { (name: String) in
            do {
                try self.setAppIconWithoutAlert(name)
                return name
            } catch {
                print("[ExpoDynamicAppIcon] Error during setting app icon: \(error.localizedDescription)")
                return "DEFAULT"
            }
        }

        Function("getAppIconIOS") {
            return UIApplication.shared.alternateIconName ?? "DEFAULT"
        }
    }

    private func setAppIconWithoutAlert(_ iconName: String?) throws {
        if UIApplication.shared.responds(
            to: #selector(getter: UIApplication.supportsAlternateIcons))
            && UIApplication.shared.supportsAlternateIcons
        {
            typealias setAlternateIconName = @convention(c) (
                NSObject, Selector, NSString?, @escaping (NSError) -> Void
            ) -> Void

            let selectorString = "_setAlternateIconName:completionHandler:"

            let selector = NSSelectorFromString(selectorString)
            let imp = UIApplication.shared.method(for: selector)
            let method = unsafeBitCast(imp, to: setAlternateIconName.self)
            method(
                UIApplication.shared, selector, iconName as NSString?, { _ in })
        }
    }
}
