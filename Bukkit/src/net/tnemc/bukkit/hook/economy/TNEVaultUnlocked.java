package net.tnemc.bukkit.hook.economy;

/*
 * The New Economy
 * Copyright (C) 2022 - 2024 Daniel "creatorfromhell" Vidmar
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import net.milkbowl.vault2.economy.AccountPermission;
import net.milkbowl.vault2.economy.Economy;
import net.milkbowl.vault2.economy.EconomyResponse;
import net.milkbowl.vault2.economy.EconomyResponse.ResponseType;
import net.tnemc.core.EconomyManager;
import net.tnemc.core.TNECore;
import net.tnemc.core.account.Account;
import net.tnemc.core.account.SharedAccount;
import net.tnemc.core.account.holdings.HoldingsEntry;
import net.tnemc.core.account.holdings.modify.HoldingsModifier;
import net.tnemc.core.account.shared.Member;
import net.tnemc.core.account.shared.MemberPermissions;
import net.tnemc.core.actions.source.PluginSource;
import net.tnemc.core.currency.Currency;
import net.tnemc.core.currency.format.CurrencyFormatter;
import net.tnemc.core.transaction.Transaction;
import net.tnemc.core.transaction.TransactionResult;
import net.tnemc.core.utils.exceptions.InvalidTransactionException;
import net.tnemc.plugincore.PluginCore;
import net.tnemc.plugincore.core.compatibility.log.DebugLevel;
import net.tnemc.plugincore.core.id.UUIDPair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * TNEVaultUnlocked
 *
 * @author creatorfromhell
 * @since 0.1.2.0
 */
public class TNEVaultUnlocked implements Economy {

  /**
   * Checks if economy plugin is enabled.
   *
   * @return true if the server's economy plugin has properly enabled.
   */
  @Override
  public boolean isEnabled() {

    return true;
  }

  /**
   * Gets name of the economy plugin.
   *
   * @return Name of the active economy plugin on the server.
   */
  @Override
  public String getName() {

    return "TheNewEconomy";
  }

  @Override
  public boolean hasSharedAccountSupport() {

    return true;
  }

  /**
   * Returns true if the economy plugin supports multiple currencies.
   *
   * @return true if the economy plugin supports multiple currencies.
   */
  @Override
  public boolean hasMultiCurrencySupport() {

    return true;
  }

  @Override
  public int fractionalDigits(final String pluginName) {

    return TNECore.eco().currency().getDefaultCurrency().getDecimalPlaces();
  }

  /**
   * Plugins use this method to format a given BigDecimal amount into a human-readable amount using
   * your economy plugin's currency names/conventions.
   *
   * @param amount to format.
   *
   * @return Human-readable string describing amount, ie 5 Dollars or 5.55 Pounds.
   */
  @Override
  public @NotNull String format(final BigDecimal amount) {
    PluginCore.log().debug("Format method called with amount: " + amount, DebugLevel.STANDARD);

    return CurrencyFormatter.format(null, new HoldingsEntry(TNECore.eco().region().defaultRegion(),
                                                            TNECore.eco().currency().getDefaultCurrency().getUid(),
                                                            amount,
                                                            EconomyManager.NORMAL
    ));
  }

  @Override
  public @NotNull String format(final String pluginName, final BigDecimal amount) {
    PluginCore.log().debug("Format method called with pluginName: " + pluginName + ", amount: " + amount, DebugLevel.STANDARD);

    return CurrencyFormatter.format(null, new HoldingsEntry(TNECore.eco().region().defaultRegion(),
                                                            TNECore.eco().currency().getDefaultCurrency().getUid(),
                                                            amount,
                                                            EconomyManager.NORMAL
    ));
  }

  /**
   * Plugins use this method to format a given BigDecimal amount into a human-readable amount using
   * your economy plugin's currency names/conventions.
   *
   * @param amount   to format.
   * @param currency the currency to use for the format.
   *
   * @return Human-readable string describing amount, ie 5 Dollars or 5.55 Pounds.
   */
  @Override
  public @NotNull String format(final BigDecimal amount, final String currency) {
    PluginCore.log().debug("Format method called with amount: " + amount + ", currency: " + currency, DebugLevel.STANDARD);

    return CurrencyFormatter.format(null, new HoldingsEntry(TNECore.eco().region().defaultRegion(),
                                                            TNECore.eco().currency().getDefaultCurrency().getUid(),
                                                            amount,
                                                            EconomyManager.NORMAL
    ));
  }

  @Override
  public @NotNull String format(final String pluginName, final BigDecimal amount, final String currency) {
    PluginCore.log().debug("Format method called with pluginName: " + pluginName + ", amount: " + amount + ", currency: " + currency, DebugLevel.STANDARD);

    return CurrencyFormatter.format(null, new HoldingsEntry(TNECore.eco().region().defaultRegion(),
                                                            TNECore.eco().currency().getDefaultCurrency().getUid(),
                                                            amount,
                                                            EconomyManager.NORMAL
    ));
  }

  @Override
  public boolean hasCurrency(final String currency) {
    PluginCore.log().debug("HasCurrency method called with currency: " + currency, DebugLevel.STANDARD);
    return TNECore.eco().currency().findCurrency(currency).isPresent();
  }

  @Override
  public @NotNull String getDefaultCurrency(final String pluginName) {
    PluginCore.log().debug("GetDefaultCurrency method called with pluginName: " + pluginName, DebugLevel.STANDARD);
    return TNECore.eco().currency().getDefaultCurrency().getIdentifier();
  }

  @Override
  public @NotNull String defaultCurrencyNamePlural(final String pluginName) {
    PluginCore.log().debug("DefaultCurrencyNamePlural method called with pluginName: " + pluginName, DebugLevel.STANDARD);
    return TNECore.eco().currency().getDefaultCurrency().getDisplayPlural();
  }

  @Override
  public @NotNull String defaultCurrencyNameSingular(final String pluginName) {
    PluginCore.log().debug("DefaultCurrencyNameSingular method called with pluginName: " + pluginName, DebugLevel.STANDARD);
    return TNECore.eco().currency().getDefaultCurrency().getDisplay();
  }

  @Override
  public Collection<String> currencies() {
    PluginCore.log().debug("Currencies method called", DebugLevel.STANDARD);
    return TNECore.eco().currency().getCurIDMap().keySet();
  }

  @Override
  public boolean createAccount(final UUID uuid, final String name) {
    PluginCore.log().debug("CreateAccount method called with UUID: " + uuid + ", Name: " + name, DebugLevel.STANDARD);
    return TNECore.eco().account().createAccount(uuid.toString(), name).getResponse().success();
  }

  @Override
  public boolean createAccount(final UUID uuid, final String name, final boolean player) {
    PluginCore.log().debug("CreateAccount method called with UUID: " + uuid + ", Name: " + name + ", Player: " + player, DebugLevel.STANDARD);
    return TNECore.eco().account().createAccount(uuid.toString(), name, !player).getResponse().success();
  }

  @Override
  public boolean createAccount(final UUID uuid, final String name, final String worldName) {
    PluginCore.log().debug("CreateAccount method called with UUID: " + uuid + ", Name: " + name + ", WorldName: " + worldName, DebugLevel.STANDARD);
    return createAccount(uuid, name);
  }

  @Override
  public boolean createAccount(final UUID uuid, final String name, final String worldName, final boolean player) {
    PluginCore.log().debug("CreateAccount method called with UUID: " + uuid + ", Name: " + name + ", WorldName: " + worldName + ", Player: " + player, DebugLevel.STANDARD);
    return createAccount(uuid, name, player);
  }

  @Override
  public Map<UUID, String> getUUIDNameMap() {
    PluginCore.log().debug("GetUUIDNameMap method called", DebugLevel.STANDARD);
    return Map.of();
  }

  @Override
  public Optional<String> getAccountName(final UUID uuid) {
    PluginCore.log().debug("GetAccountName method called with UUID: " + uuid, DebugLevel.STANDARD);
    final Optional<Account> accountOpt = TNECore.eco().account().findAccount(uuid);
    return accountOpt.map(Account::getName);
  }

  @Override
  public boolean hasAccount(final UUID uuid) {
    PluginCore.log().debug("HasAccount method called with UUID: " + uuid, DebugLevel.STANDARD);
    return TNECore.eco().account().findAccount(uuid.toString()).isPresent();
  }

  @Override
  public boolean hasAccount(final UUID uuid, final String worldName) {
    PluginCore.log().debug("HasAccount method called with UUID: " + uuid + ", WorldName: " + worldName, DebugLevel.STANDARD);
    return hasAccount(uuid);
  }

  @Override
  public boolean renameAccount(final UUID uuid, final String name) {
    PluginCore.log().debug("RenameAccount method called with UUID: " + uuid + ", Name: " + name, DebugLevel.STANDARD);
    return renameAccount("vault-unlocked", uuid, name);
  }

  @Override
  public boolean renameAccount(final String plugin, final UUID accountID, final String name) {
    PluginCore.log().debug("RenameAccount method called with Plugin: " + plugin + ", AccountID: " + accountID + ", Name: " + name, DebugLevel.STANDARD);
    return false;
  }

  @Override
  public boolean deleteAccount(final String plugin, final UUID accountID) {
    PluginCore.log().debug("DeleteAccount method called with Plugin: " + plugin + ", AccountID: " + accountID, DebugLevel.STANDARD);
    return TNECore.eco().account().deleteAccount(accountID).success();
  }

  @Override
  public boolean accountSupportsCurrency(final String plugin, final UUID accountID, final String currency) {
    PluginCore.log().debug("AccountSupportsCurrency method called with Plugin: " + plugin + ", AccountID: " + accountID + ", Currency: " + currency, DebugLevel.STANDARD);
    return true;
  }

  @Override
  public boolean accountSupportsCurrency(final String plugin, final UUID accountID, final String currency, final String world) {
    PluginCore.log().debug("AccountSupportsCurrency method called with Plugin: " + plugin + ", AccountID: " + accountID + ", Currency: " + currency + ", World: " + world, DebugLevel.STANDARD);
    return true;
  }

  @Override
  public BigDecimal getBalance(final String pluginName, final UUID uuid) {
    PluginCore.log().debug("GetBalance method called with PluginName: " + pluginName + ", UUID: " + uuid, DebugLevel.STANDARD);
    return getBalance(pluginName, uuid, TNECore.eco().region().defaultRegion(), getDefaultCurrency(pluginName));
  }

  @Override
  public BigDecimal getBalance(final String pluginName, final UUID uuid, final String world) {
    PluginCore.log().debug("GetBalance method called with PluginName: " + pluginName + ", UUID: " + uuid + ", World: " + world, DebugLevel.STANDARD);
    return getBalance(pluginName, uuid, world, getDefaultCurrency(pluginName));
  }

  @Override
  public BigDecimal getBalance(final String pluginName, final UUID uuid, final String world, final String currency) {
    PluginCore.log().debug("GetBalance method called with PluginName: " + pluginName + ", UUID: " + uuid + ", World: " + world + ", Currency: " + currency, DebugLevel.STANDARD);

    final Optional<Account> account = TNECore.eco().account().findAccount(uuid.toString());
    final Optional<Currency> currencyOpt = TNECore.eco().currency().findCurrency(currency);

    if (account.isPresent() && currencyOpt.isPresent()) {
      PluginCore.log().debug("Account exists. Name: " + account.get().getName(), DebugLevel.STANDARD);
      return account.get().getHoldingsTotal(world, currencyOpt.get().getUid());
    }

    PluginCore.log().debug("Account does not exist. UUID: " + uuid, DebugLevel.STANDARD);
    return BigDecimal.ZERO;
  }

  @Override
  public boolean has(final String pluginName, final UUID uuid, final BigDecimal amount) {
    PluginCore.log().debug("Has method called with PluginName: " + pluginName + ", UUID: " + uuid + ", Amount: " + amount, DebugLevel.STANDARD);
    return has(pluginName, uuid, TNECore.eco().region().defaultRegion(), getDefaultCurrency(pluginName), amount);
  }

  @Override
  public boolean has(final String pluginName, final UUID uuid, final String worldName, final BigDecimal amount) {
    PluginCore.log().debug("Has method called with PluginName: " + pluginName + ", UUID: " + uuid + ", WorldName: " + worldName + ", Amount: " + amount, DebugLevel.STANDARD);
    return has(pluginName, uuid, worldName, getDefaultCurrency(pluginName), amount);
  }

  @Override
  public boolean has(final String pluginName, final UUID uuid, final String worldName, final String currency, final BigDecimal amount) {
    PluginCore.log().debug("Has method called with PluginName: " + pluginName + ", UUID: " + uuid + ", WorldName: " + worldName + ", Currency: " + currency + ", Amount: " + amount, DebugLevel.STANDARD);

    final Optional<Account> account = TNECore.eco().account().findAccount(uuid.toString());
    final Optional<Currency> currencyOpt = TNECore.eco().currency().findCurrency(currency);

    return currencyOpt.filter(currency1 -> account.filter(value -> value.getHoldingsTotal(worldName, currency1.getUid())
                                                                           .compareTo(amount) >= 0).isPresent()).isPresent();
  }

  @Override
  public EconomyResponse withdraw(final String pluginName, final UUID uuid, final BigDecimal amount) {
    PluginCore.log().debug("Withdraw method called with PluginName: " + pluginName + ", UUID: " + uuid + ", Amount: " + amount, DebugLevel.STANDARD);
    return withdraw(pluginName, uuid, TNECore.eco().region().defaultRegion(), getDefaultCurrency(pluginName), amount);
  }

  @Override
  public EconomyResponse withdraw(final String pluginName, final UUID uuid, final String worldName, final BigDecimal amount) {
    PluginCore.log().debug("Withdraw method called with PluginName: " + pluginName + ", UUID: " + uuid + ", WorldName: " + worldName + ", Amount: " + amount, DebugLevel.STANDARD);
    return withdraw(pluginName, uuid, worldName, getDefaultCurrency(pluginName), amount);
  }

  @Override
  public EconomyResponse withdraw(final String pluginName, final UUID uuid, final String worldName, final String currency, final BigDecimal amount) {
    PluginCore.log().debug("Withdraw method called with PluginName: " + pluginName + ", UUID: " + uuid + ", WorldName: " + worldName + ", Currency: " + currency + ", Amount: " + amount, DebugLevel.STANDARD);

    final Optional<Account> account = TNECore.eco().account().findAccount(uuid.toString());

    if (account.isEmpty()) {
      return new EconomyResponse(BigDecimal.ZERO, BigDecimal.ZERO,
                                 ResponseType.FAILURE, "Unable to locate associated account.");
    }

    final HoldingsModifier modifier = new HoldingsModifier(worldName,
                                                           TNECore.eco().currency().getDefaultCurrency(worldName).getUid(),
                                                           amount);

    final Transaction transaction = new Transaction("take")
            .to(account.get(), modifier.counter())
            .processor(EconomyManager.baseProcessor())
            .source(new PluginSource(pluginName));

    try {
      final TransactionResult result = transaction.process();
      return new EconomyResponse(amount, transaction.getTo().getCombinedEnding(),
                                 fromResult(result),
                                 result.getMessage());
    } catch (final InvalidTransactionException e) {
      return new EconomyResponse(amount, transaction.getTo().getCombinedEnding(),
                                 ResponseType.FAILURE, e.getMessage());
    }
  }

  @Override
  public EconomyResponse deposit(final String pluginName, final UUID uuid, final BigDecimal amount) {
    PluginCore.log().debug("Deposit method called with PluginName: " + pluginName + ", UUID: " + uuid + ", Amount: " + amount, DebugLevel.STANDARD);
    return deposit(pluginName, uuid, TNECore.eco().region().defaultRegion(), getDefaultCurrency(pluginName), amount);
  }

  @Override
  public EconomyResponse deposit(final String pluginName, final UUID uuid, final String worldName, final BigDecimal amount) {
    PluginCore.log().debug("Deposit method called with PluginName: " + pluginName + ", UUID: " + uuid + ", WorldName: " + worldName + ", Amount: " + amount, DebugLevel.STANDARD);
    return deposit(pluginName, uuid, worldName, getDefaultCurrency(pluginName), amount);
  }

  @Override
  public EconomyResponse deposit(final String pluginName, final UUID uuid, final String worldName, final String currency, final BigDecimal amount) {
    PluginCore.log().debug("Deposit method called with PluginName: " + pluginName + ", UUID: " + uuid + ", WorldName: " + worldName + ", Currency: " + currency + ", Amount: " + amount, DebugLevel.STANDARD);

    final Optional<Account> account = TNECore.eco().account().findAccount(uuid.toString());

    if (account.isEmpty()) {
      return new EconomyResponse(BigDecimal.ZERO, BigDecimal.ZERO, ResponseType.FAILURE, "Unable to locate associated account.");
    }

    final HoldingsModifier modifier = new HoldingsModifier(worldName,
                                                           TNECore.eco().currency().getDefaultCurrency(worldName).getUid(),
                                                           amount);

    final Transaction transaction = new Transaction("give")
            .to(account.get(), modifier)
            .processor(EconomyManager.baseProcessor())
            .source(new PluginSource(pluginName));

    try {
      final TransactionResult result = transaction.process();
      return new EconomyResponse(amount, transaction.getTo().getCombinedEnding(), fromResult(result),
                                 result.getMessage());
    } catch (final InvalidTransactionException e) {
      return new EconomyResponse(amount, transaction.getTo().getCombinedEnding(),
                                 ResponseType.FAILURE, e.getMessage());
    }
  }

  @Override
  public boolean createSharedAccount(final String pluginName, final UUID accountID, final String name, final UUID owner) {

    if(TNECore.eco().account().findAccount(name).isPresent() || TNECore.eco().account().findAccount(accountID.toString()).isPresent()) {

      return false;
    }

    final SharedAccount account = new SharedAccount(accountID, name, owner);

    TNECore.eco().account().getAccounts().put(account.getIdentifier().toString(), account);
    TNECore.eco().account().uuidProvider().store(new UUIDPair(accountID, name));

    return true;
  }

  @Override
  public boolean isAccountOwner(final String pluginName, final UUID accountID, final UUID uuid) {

    final Optional<Account> account = TNECore.eco().account().findAccount(accountID.toString());
    if(account.isEmpty()) {

      return false;
    }

    if(account.get() instanceof final SharedAccount shared) {

      return shared.getOwner().equals(uuid);
    }

    return false;
  }

  @Override
  public boolean setOwner(final String pluginName, final UUID accountID, final UUID uuid) {

    final Optional<Account> account = TNECore.eco().account().findAccount(accountID.toString());
    if(account.isEmpty()) {

      return false;
    }

    if(account.get() instanceof final SharedAccount shared) {

      shared.setOwner(uuid);

      return true;
    }

    return false;
  }

  @Override
  public boolean isAccountMember(final String pluginName, final UUID accountID, final UUID uuid) {

    final Optional<Account> account = TNECore.eco().account().findAccount(accountID.toString());
    if(account.isEmpty()) {

      return false;
    }

    if(account.get() instanceof final SharedAccount shared) {

      return shared.getOwner().equals(uuid) || shared.isMember(uuid);
    }

    return false;
  }

  @Override
  public boolean addAccountMember(final String pluginName, final UUID accountID, final UUID uuid) {

    final Optional<Account> account = TNECore.eco().account().findAccount(accountID.toString());
    if(account.isEmpty()) {

      return false;
    }

    if(account.get() instanceof final SharedAccount shared) {

      final Member member = new Member(uuid);
      shared.getMembers().put(uuid, member);
      return true;
    }

    return false;
  }

  @Override
  public boolean addAccountMember(final String pluginName, final UUID accountID, final UUID uuid, final AccountPermission... initialPermissions) {

    final Optional<Account> account = TNECore.eco().account().findAccount(accountID.toString());
    if(account.isEmpty()) {

      return false;
    }

    if(account.get() instanceof final SharedAccount shared) {

      final Member member = new Member(uuid);

      for(final AccountPermission permission : initialPermissions) {

        final MemberPermissions perm = permissionConversion(permission);
        if(perm != null) {

          member.addPermission(perm, true);
        }

      }

      shared.getMembers().put(uuid, member);
      return true;
    }

    return false;
  }

  @Override
  public boolean removeAccountMember(final String pluginName, final UUID accountID, final UUID uuid) {

    final Optional<Account> account = TNECore.eco().account().findAccount(accountID.toString());
    if(account.isEmpty()) {

      return false;
    }

    if(account.get() instanceof final SharedAccount shared) {

      shared.getMembers().remove(uuid);
      return true;
    }

    return false;
  }

  @Override
  public boolean hasAccountPermission(final String pluginName, final UUID accountID, final UUID uuid, final AccountPermission permission) {

    final Optional<Account> account = TNECore.eco().account().findAccount(accountID.toString());
    final MemberPermissions perm = permissionConversion(permission);
    if(account.isEmpty() || perm == null) {

      return false;
    }

    if(account.get() instanceof final SharedAccount shared) {

      final Optional<Member> member = shared.findMember(uuid);
      if(member.isPresent()) {

        return member.get().hasPermission(perm);
      }
    }

    return false;
  }

  @Override
  public boolean updateAccountPermission(final String pluginName, final UUID accountID, final UUID uuid, final AccountPermission permission, final boolean value) {

    final Optional<Account> account = TNECore.eco().account().findAccount(accountID.toString());
    final MemberPermissions perm = permissionConversion(permission);
    if(account.isEmpty() || perm == null) {

      return false;
    }

    if(account.get() instanceof final SharedAccount shared) {

      final Optional<Member> member = shared.findMember(uuid);
      if(member.isPresent()) {

        member.get().addPermission(perm, value);
        return true;
      }
    }

    return false;
  }

  private MemberPermissions permissionConversion(final AccountPermission permission) {

    return switch(permission) {
      case DEPOSIT -> MemberPermissions.DEPOSIT;
      case WITHDRAW -> MemberPermissions.WITHDRAW;
      case BALANCE -> MemberPermissions.BALANCE;
      case TRANSFER_OWNERSHIP -> MemberPermissions.TRANSFER_OWNERSHIP;
      case INVITE_MEMBER -> MemberPermissions.ADD_MEMBER;
      case REMOVE_MEMBER -> MemberPermissions.REMOVE_MEMBER;
      case CHANGE_MEMBER_PERMISSION -> MemberPermissions.MODIFY_MEMBER;
      case OWNER -> MemberPermissions.OWNERSHIP;
      case DELETE -> MemberPermissions.DELETE_ACCOUNT;
    };
  }

  private ResponseType fromResult(@Nullable final TransactionResult result) {

    if(result != null && result.isSuccessful()) {
      return ResponseType.SUCCESS;
    }
    return ResponseType.FAILURE;
  }
}
